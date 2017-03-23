package me.welkinbai.bsonmapper;

import me.welkinbai.bsonmapper.annotations.BsonArrayField;
import me.welkinbai.bsonmapper.annotations.BsonField;
import me.welkinbai.bsonmapper.annotations.BsonIgnore;
import me.welkinbai.bsonmapper.exception.BsonMapperConverterException;
import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonDouble;
import org.bson.BsonString;
import org.bson.BsonValue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by baixiaoxuan on 2017/3/23.
 */
class BsonDocumentConverter {

    private BsonDocumentConverter() {
    }

    static BsonDocumentConverter getInstance() {
        return new BsonDocumentConverter();
    }

    <T> T decode(BsonDocument bsonDocument, Class<T> targetClazz) {
        if (bsonDocument == null) {
            return null;
        }
        List<Field> allField = Utils.getAllField(targetClazz);
        T target = Utils.newInstanceByClazz(targetClazz);
        for (Field field : allField) {
            if (isIgnored(field)) {
                continue;
            }
            BsonValue bsonValue = bsonDocument.get(getBsonName(field));
            if (bsonValue == null) {
                continue;
            }
            Object javaValue = getJavaValueFromBsonValue(bsonValue, field);
            if (Modifier.isPrivate(field.getModifiers())) {
                field.setAccessible(true);
            }
            try {
                field.set(target, javaValue);
            } catch (IllegalAccessException e1) {
                try {
                    setBySetter(targetClazz.getDeclaredMethod(Utils.makeSetterName(field.getName())), target, javaValue);
                } catch (NoSuchMethodException e) {
                    throw new BsonMapperConverterException("need Setter for field:" + field.getName());
                }
            }
        }
        return target;
    }

    private <T> void setBySetter(Method setter, T target, Object javaValue) {
        Utils.methodInvoke(setter, target, javaValue);
    }

    private Object getJavaValueFromBsonValue(BsonValue bsonValue, Field field) {
        if (bsonValue.isArray()) {
            return decode(bsonValue.asArray(), field);
        }
        return getJavaValueFromBsonValue(bsonValue, field.getType());
    }

    private Object getJavaValueFromBsonValue(BsonValue bsonValue, Class<?> targetClass) {
        switch (bsonValue.getBsonType()) {
            case DOUBLE:
                return bsonValue.asDouble().getValue();
            case STRING:
                return bsonValue.asString().getValue();
            case DOCUMENT:
                return decode(bsonValue.asDocument(), targetClass);
            case ARRAY:
                throw new IllegalArgumentException("should never be happen.");
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    <T> T decode(BsonArray bsonArray, Field field) {
        Class<?> fieldType = field.getType();
        if (fieldType.isArray()) {
            return (T) bsonArray.toArray();
        } else if (Collection.class.isAssignableFrom(fieldType)) {
            if (Utils.isUnableAddCollectionClazz(fieldType)) {
                return null;
            }
            Class<?> implClass = Utils.giveImplClass(fieldType);
            BsonArrayField bsonArrayField = field.getAnnotation(BsonArrayField.class);
            if (bsonArrayField == null) {
                throw new BsonMapperConverterException(String.format("field %s need @BsonArrayField for Collection field.If you don't want to decode the field, add @BsonIgnore for it.", field.getName()));
            }
            return decode(bsonArray, bsonArrayField.componentType(), implClass);
        } else {
            throw new BsonMapperConverterException(String.format("field %s should be array or Collection because there is a BsonArray in BsonDocument,BsonName is %s", field.getName(), getBsonName(field)));
        }
    }

    @SuppressWarnings("unchecked")
    <T> T decode(BsonArray bsonArray, Class<?> targetComponentClazz, Class<?> fieldType) {
        Object collectionObject = null;
        for (BsonValue bsonValue : bsonArray) {
            if (bsonValue.isArray()) {
                BsonArray array = bsonValue.asArray();
                return decode(array, targetComponentClazz, fieldType);
            } else {
                collectionObject = Utils.newInstanceByClazz(fieldType);
                Object javaValue = getJavaValueFromBsonValue(bsonValue, targetComponentClazz);
                Method method = null;
                try {
                    method = fieldType.getMethod("add", Object.class);
                } catch (NoSuchMethodException e) {
                    throw new BsonMapperConverterException("NoSuchMethodException", e);
                }
                Utils.methodInvoke(method, collectionObject, javaValue);
            }
        }
        return (T) collectionObject;
    }

    private String getBsonName(Field field) {
        String bsonName = field.getName();
        BsonField bsonField = field.getAnnotation(BsonField.class);
        if (bsonField != null) {
            bsonName = bsonField.value();
        }
        return bsonName;
    }

    private boolean isIgnored(Field field) {
        BsonIgnore bsonIgnore = field.getAnnotation(BsonIgnore.class);
        return bsonIgnore != null;
    }

    public static void main(String[] args) {
        List<BsonString> arrayList = new ArrayList<BsonString>();
        arrayList.add(new BsonString("testArrayStrV"));
        BsonDocument bsonDocument = new BsonDocument().append("testDouble", new BsonDouble(20.99))
                .append("testString", new BsonString("testStringV"))
                .append("testArray", new BsonArray(arrayList));
        BsonTest bsonTest = BsonDocumentConverter.getInstance().decode(bsonDocument, BsonTest.class);
        System.out.println(bsonTest.getTestDouble());
        System.out.println(bsonTest.getTestString());
        System.out.println(bsonTest.getTestArray());
    }

    public static class BsonTest {
        private Double testDouble;
        private String testString;
        @BsonArrayField(componentType = String.class)
        private List<String> testArray;

        public Double getTestDouble() {
            return testDouble;
        }

        public String getTestString() {
            return testString;
        }

        public List<String> getTestArray() {
            return testArray;
        }
    }

}
