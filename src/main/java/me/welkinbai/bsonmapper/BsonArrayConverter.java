package me.welkinbai.bsonmapper;

import me.welkinbai.bsonmapper.annotations.BsonArrayField;
import me.welkinbai.bsonmapper.exception.BsonMapperConverterException;
import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.types.ObjectId;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by welkinbai on 2017/3/23.
 */
class BsonArrayConverter {

    private BsonArrayConverter() {
    }

    static BsonArrayConverter getInstance() {
        return new BsonArrayConverter();
    }

    Object decode(BsonArray bsonArray, Field field) {
        Class<?> fieldType = field.getType();
        if (fieldType.isArray()) {
            return handleArray(bsonArray, field.getType());
        } else if (Collection.class.isAssignableFrom(fieldType)) {
            Class<?> implClass = Utils.giveImplClassIfSupport(fieldType);
            if (Utils.isUnableAddCollectionClazz(implClass)) {
                return null;
            }
            BsonArrayField bsonArrayField = Utils.getBsonArrayFieldAnnotation(field);
            return decode(bsonArray, bsonArrayField.componentType(), implClass);
        } else {
            throw new BsonMapperConverterException(String.format("field %s should be array or Collection because there is a BsonArray in BsonDocument,BsonName is %s", field.getName(), Utils.getBsonName(field)));
        }
    }

    Object decode(BsonReader bsonReader, Field field) {
        Class<?> fieldType = field.getType();
        if (fieldType.isArray()) {
            return handleArray(bsonReader, field.getType());
        } else if (Collection.class.isAssignableFrom(fieldType)) {
            Class<?> implClass = Utils.giveImplClassIfSupport(fieldType);
            if (Utils.isUnableAddCollectionClazz(implClass)) {
                return null;
            }
            BsonArrayField bsonArrayField = Utils.getBsonArrayFieldAnnotation(field);
            return decode(bsonReader, bsonArrayField.componentType(), implClass);
        } else {
            throw new BsonMapperConverterException(String.format("field %s should be array or Collection because there is a BsonArray in BsonDocument,BsonName is %s", field.getName(), Utils.getBsonName(field)));
        }
    }


    private Object handleArray(BsonReader bsonReader, Class<?> fieldType) {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        bsonReader.readStartArray();
        while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            BsonType currentBsonType = bsonReader.getCurrentBsonType();
            if (currentBsonType == BsonType.ARRAY) {
                arrayList.add(handleArray(bsonReader, fieldType.getComponentType()));
            } else {
                Object javaValue = BsonValueConverterRepertory.getByteIOConverterByBsonType(currentBsonType).decode(bsonReader);
                arrayList.add(javaValue);
            }
        }
        bsonReader.readEndArray();
        return arrayList.toArray((Object[]) Array.newInstance(fieldType.getComponentType(), 0));
    }

    private Object handleArray(BsonArray bsonArray, Class<?> fieldType) {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        BsonValue bsonValue = bsonArray.get(0);
        if (bsonValue != null && bsonValue.isArray()) {
            for (BsonValue value : bsonArray) {
                arrayList.add(handleArray(value.asArray(), fieldType.getComponentType()));
            }
        } else {
            for (BsonValue value : bsonArray) {
                Object javaValue = BsonValueConverterRepertory.getValueConverterByBsonType(value.getBsonType()).decode(bsonValue);
                arrayList.add(javaValue);
            }
        }
        return arrayList.toArray((Object[]) Array.newInstance(fieldType.getComponentType(), 0));
    }

    Object decode(BsonArray bsonArray, Class<?> targetComponentClazz, Class<?> targetType) {
        Object collectionObject = Utils.newInstanceByClazz(targetType);
        Method method = null;
        try {
            method = targetType.getMethod("add", Object.class);
        } catch (NoSuchMethodException e) {
            throw new BsonMapperConverterException("NoSuchMethodException", e);
        }
        for (BsonValue bsonValue : bsonArray) {
            if (bsonValue.isArray()) {
                BsonArray array = bsonValue.asArray();
                Utils.methodInvoke(method, collectionObject, decode(array, targetComponentClazz, targetType));
            } else {
                Object javaValue;
                if (bsonValue.isDocument()) {
                    javaValue = BsonValueConverterRepertory.getBsonDocumentConverter().decode(bsonValue.asDocument(), targetComponentClazz);
                } else {
                    javaValue = BsonValueConverterRepertory.getValueConverterByBsonType(bsonValue.getBsonType()).decode(bsonValue);
                }
                Utils.methodInvoke(method, collectionObject, javaValue);
            }
        }
        return collectionObject;
    }

    Object decode(BsonReader bsonReader, Class<?> targetComponentClazz, Class<?> targetType) {
        Object collectionObject = Utils.newInstanceByClazz(targetType);
        Method method = null;
        try {
            method = targetType.getMethod("add", Object.class);
        } catch (NoSuchMethodException e) {
            throw new BsonMapperConverterException("NoSuchMethodException", e);
        }
        bsonReader.readStartArray();
        while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            BsonType currentBsonType = bsonReader.getCurrentBsonType();
            if (currentBsonType == BsonType.ARRAY) {
                Utils.methodInvoke(method, collectionObject, decode(bsonReader, targetComponentClazz, targetType));
            } else {
                Object javaValue;
                if (currentBsonType == BsonType.DOCUMENT) {
                    javaValue = BsonValueConverterRepertory.getBsonDocumentConverter().decode(bsonReader, targetComponentClazz);
                } else if (currentBsonType == BsonType.OBJECT_ID) {
                    ObjectId objectId = (ObjectId) BsonValueConverterRepertory.getByteIOConverterByBsonType(currentBsonType).decode(bsonReader);
                    javaValue = Utils.getObjectIdByRealType(targetComponentClazz, objectId);
                } else {
                    javaValue = BsonValueConverterRepertory.getByteIOConverterByBsonType(currentBsonType).decode(bsonReader);
                }
                Utils.methodInvoke(method, collectionObject, javaValue);
            }
        }
        bsonReader.readEndArray();
        return collectionObject;
    }

    public void encode(BsonDocument bsonDocument, Field field, Object fieldValue) {
        Class<?> fieldType = field.getType();
        String bsonName = Utils.getBsonName(field);
        ArrayList<BsonValue> arrayList = new ArrayList<BsonValue>();
        if (fieldType.isArray()) {
            Class<?> componentType = fieldType.getComponentType();
            Object[] arrayValue = (Object[]) fieldValue;
            for (Object o : arrayValue) {
                fillListByValueForArrayElement(arrayList, componentType, o);
            }
        } else if (Collection.class.isAssignableFrom(fieldType)) {
            BsonArrayField fieldAnnotation = Utils.getBsonArrayFieldAnnotation(field);
            Class<?> componentType = fieldAnnotation.componentType();
            if (Set.class.isAssignableFrom(fieldType) || List.class.isAssignableFrom(fieldType)) {
                Collection fieldSet = (Collection) fieldValue;
                for (Object o : fieldSet) {
                    fillListByValueForArrayElement(arrayList, componentType, o);
                }
            } else {
                return;
            }
        } else {
            throw new BsonMapperConverterException("exception should be never happen.the field:" + bsonName + "should be array or Collection");
        }
        bsonDocument.put(bsonName, new BsonArray(arrayList));
    }

    private void fillListByValueForArrayElement(ArrayList<BsonValue> arrayList, Class<?> componentType, Object o) {
        if (componentType.isInstance(o)) {
            if (BsonValueConverterRepertory.isCanConverterValueType(componentType)) {
                arrayList.add(BsonValueConverterRepertory.getValueConverterByClazz(componentType).encode(o));
            } else {
                BsonDocument arrayEle = new BsonDocument();
                BsonValueConverterRepertory.getBsonDocumentConverter().encode(arrayEle, o);
                arrayList.add(arrayEle);
            }
        }
    }

    public void encode(BsonWriter bsonWriter, Field field, Object fieldValue) {
        bsonWriter.writeStartArray();
        Class<?> fieldType = field.getType();
        if (fieldType.isArray()) {
            Class<?> componentType = fieldType.getComponentType();
            Object[] arrayValue = (Object[]) fieldValue;
            for (Object value : arrayValue) {
                writeByValueForArrayElement(bsonWriter, componentType, value);
            }
        } else if (Collection.class.isAssignableFrom(fieldType)) {
            BsonArrayField fieldAnnotation = Utils.getBsonArrayFieldAnnotation(field);
            Class<?> componentType = fieldAnnotation.componentType();
            if (Set.class.isAssignableFrom(fieldType) || List.class.isAssignableFrom(fieldType)) {
                Collection fieldSet = (Collection) fieldValue;
                for (Object value : fieldSet) {
                    writeByValueForArrayElement(bsonWriter, componentType, value);
                }
            } else {
                return;
            }
        } else {
            throw new BsonMapperConverterException("exception should be never happen.the field:" + Utils.getBsonName(field) + "should be array or Collection");
        }
        bsonWriter.writeEndArray();
    }

    @SuppressWarnings("unchecked")
    private void writeByValueForArrayElement(BsonWriter bsonWriter, Class<?> componentType, Object value) {
        if (componentType.isInstance(value)) {
            if (BsonValueConverterRepertory.isCanConverterValueType(componentType)) {
                BsonValueConverterRepertory.getByteIOConverterByClazz(componentType).encode(bsonWriter, value);
            } else {
                BsonValueConverterRepertory.getBsonDocumentConverter().encode(bsonWriter, value);
            }
        }
    }
}
