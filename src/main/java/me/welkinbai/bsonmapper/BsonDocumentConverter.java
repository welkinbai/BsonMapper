package me.welkinbai.bsonmapper;

import me.welkinbai.bsonmapper.exception.BsonMapperConverterException;
import org.bson.BsonDocument;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.types.ObjectId;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static me.welkinbai.bsonmapper.Utils.getBsonName;
import static me.welkinbai.bsonmapper.Utils.getObjectIdByRealType;
import static me.welkinbai.bsonmapper.Utils.isIgnored;

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
        List<Field> allField = Utils.getAllField(targetClazz);
        T target = Utils.newInstanceByClazz(targetClazz);
        for (Field field : allField) {
            if (isIgnored(field)) {
                continue;
            }
            String bsonName = getBsonName(field);
            BsonValue bsonValue = bsonDocument.get(bsonName);
            if (bsonValue == null || bsonValue.isNull()) {
                continue;
            }
            Object javaValue;
            try {
                javaValue = getJavaValueFromBsonValue(bsonValue, field);
            } catch (BsonMapperConverterException e) {
                throw new BsonMapperConverterException("error when try to get java value from Bson.BsonName:" + bsonName, e);
            }
            setJavaValueToField(targetClazz, target, field, javaValue);
        }
        return target;
    }

    private <T> void setJavaValueToField(Class<T> targetClazz, T target, Field field, Object javaValue) {
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

    <T> T decode(BsonReader bsonReader, Class<T> targetClazz) {
        bsonReader.readStartDocument();
        Map<String, Field> bsonNameFieldInfoMap = getBsonNameFieldInfoMap(targetClazz);
        T target = Utils.newInstanceByClazz(targetClazz);
        while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String bsonName = bsonReader.readName();
            Field field = bsonNameFieldInfoMap.get(bsonName);
            if (field == null || bsonReader.getCurrentBsonType() == BsonType.NULL) {
                bsonReader.skipValue();
                continue;
            }
            Object javaValue;
            try {
                javaValue = getJavaValueByBinaryReader(bsonReader, field);
            } catch (BsonMapperConverterException e) {
                throw new BsonMapperConverterException("error when try to get java value from Bson.BsonName:" + bsonName, e);
            }
            setJavaValueToField(targetClazz, target, field, javaValue);
        }
        bsonReader.readEndDocument();
        return target;
    }

    private <T> Map<String, Field> getBsonNameFieldInfoMap(Class<T> targetClazz) {
        List<Field> allField = Utils.getAllField(targetClazz);
        Map<String, Field> bsonNameFieldInfoMap = new HashMap<String, Field>();
        for (Field field : allField) {
            String bsonName = Utils.getBsonName(field);
            bsonNameFieldInfoMap.put(bsonName, field);
        }
        return bsonNameFieldInfoMap;
    }

    private <T> void setBySetter(Method setter, T target, Object javaValue) {
        Utils.methodInvoke(setter, target, javaValue);
    }

    private Object getJavaValueByBinaryReader(BsonReader bsonReader, Field field) {
        BsonType currentBsonType = bsonReader.getCurrentBsonType();
        if (currentBsonType == BsonType.DOCUMENT) {
            return decode(bsonReader, field.getType());
        }
        if (currentBsonType == BsonType.ARRAY) {
            return BsonValueConverterRepertory.getBsonArrayConverter().decode(bsonReader, field);
        }
        if (currentBsonType == BsonType.OBJECT_ID) {
            ObjectId objectId = (ObjectId) BsonValueConverterRepertory.getByteIOConverterByBsonType(currentBsonType).decode(bsonReader);
            return getObjectIdByRealType(field.getType(), objectId);
        }
        return BsonValueConverterRepertory.getByteIOConverterByBsonType(currentBsonType).decode(bsonReader);
    }

    private Object getJavaValueFromBsonValue(BsonValue bsonValue, Field field) {
        if (bsonValue.isArray()) {
            return BsonValueConverterRepertory.getBsonArrayConverter().decode(bsonValue.asArray(), field);
        }
        if (bsonValue.isDocument()) {
            return BsonValueConverterRepertory.getBsonDocumentConverter().decode(bsonValue.asDocument(), field.getType());
        }
        if (bsonValue.isObjectId() && Utils.fieldIsObjectId(field)) {
            ObjectId objectId = (ObjectId) BsonValueConverterRepertory.getValueConverterByBsonType(bsonValue.getBsonType()).decode(bsonValue);
            return getObjectIdByRealType(field.getType(), objectId);
        }
        return BsonValueConverterRepertory.getValueConverterByBsonType(bsonValue.getBsonType()).decode(bsonValue);
    }

    public void encode(BsonDocument bsonDocument, Object object) {
        Map<String, Field> bsonNameFieldInfoMap = getBsonNameFieldInfoMap(object.getClass());
        for (Entry<String, Field> entry : bsonNameFieldInfoMap.entrySet()) {
            String bsonName = entry.getKey();
            Field field = entry.getValue();
            Object fieldValue = Utils.getFieldValue(field, object);
            if (fieldValue == null || Utils.isIgnored(field)) {
                continue;
            }
            Class<?> fieldType = field.getType();
            if (Utils.isArrayField(fieldType)) {
                BsonValueConverterRepertory.getBsonArrayConverter().encode(bsonDocument, field, fieldValue);
                continue;
            }
            if (BsonValueConverterRepertory.isCanConverterValueType(fieldType)) {
                if (Utils.fieldIsObjectId(field)) {
                    fieldType = ObjectId.class;
                }
                BsonValueConverter valueConverter = BsonValueConverterRepertory.getValueConverterByClazz(fieldType);
                if (valueConverter != null) {
                    bsonDocument.put(bsonName, valueConverter.encode(fieldValue));
                } else {
                    //              maybe log warn message to remind user add converter
                }
            } else {
                BsonDocument valueDocument = new BsonDocument();
                BsonValueConverterRepertory.getBsonDocumentConverter().encode(valueDocument, fieldValue);
                bsonDocument.put(bsonName, valueDocument);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void encode(BsonWriter bsonWriter, Object object) {
        bsonWriter.writeStartDocument();
        Map<String, Field> bsonNameFieldInfoMap = getBsonNameFieldInfoMap(object.getClass());
        for (Entry<String, Field> entry : bsonNameFieldInfoMap.entrySet()) {
            Field field = entry.getValue();
            Object fieldValue = Utils.getFieldValue(field, object);
            if (fieldValue == null || Utils.isIgnored(field)) {
                continue;
            }
            String bsonName = entry.getKey();
            bsonWriter.writeName(bsonName);
            Class<?> fieldType = field.getType();
            if (Utils.isArrayField(fieldType)) {
                BsonValueConverterRepertory.getBsonArrayConverter().encode(bsonWriter, field, fieldValue);
                continue;
            }
            if (BsonValueConverterRepertory.isCanConverterValueType(fieldType)) {
                if (Utils.fieldIsObjectId(field)) {
                    if (String.class.isAssignableFrom(fieldType)) {
                        fieldValue = new ObjectId((String) fieldValue);
                    }
                    fieldType = ObjectId.class;
                }
                BsonByteIOConverter valueConverter = BsonValueConverterRepertory.getByteIOConverterByClazz(fieldType);
                if (valueConverter != null) {
                    valueConverter.encode(bsonWriter, fieldValue);
                } else {
                    //              maybe log warn message to remind user add converter
                }
            } else {
                BsonValueConverterRepertory.getBsonDocumentConverter().encode(bsonWriter, fieldValue);
            }
        }
        bsonWriter.writeEndDocument();
    }

}
