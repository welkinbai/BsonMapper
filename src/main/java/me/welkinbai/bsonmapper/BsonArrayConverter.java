package me.welkinbai.bsonmapper;

import me.welkinbai.bsonmapper.annotations.BsonArrayField;
import me.welkinbai.bsonmapper.exception.BsonMapperConverterException;
import org.bson.BsonArray;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonValue;
import org.bson.types.ObjectId;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

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
            BsonArrayField bsonArrayField = field.getAnnotation(BsonArrayField.class);
            if (bsonArrayField == null) {
                throw new BsonMapperConverterException(String.format("field %s need @BsonArrayField for Collection field.If you don't want to decode the field, add @BsonIgnore for it.", field.getName()));
            }
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
            BsonArrayField bsonArrayField = field.getAnnotation(BsonArrayField.class);
            if (bsonArrayField == null) {
                throw new BsonMapperConverterException(String.format("field %s need @BsonArrayField for Collection field.If you don't want to decode the field, add @BsonIgnore for it.", field.getName()));
            }
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
                Object javaValue = BsonValueConverterRepertory.getBinaryReaderConverterByBsonType(currentBsonType).decode(bsonReader);
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
                    ObjectId objectId = (ObjectId) BsonValueConverterRepertory.getBinaryReaderConverterByBsonType(currentBsonType).decode(bsonReader);
                    javaValue = Utils.getObjectIdByRealType(targetComponentClazz, objectId);
                } else {
                    javaValue = BsonValueConverterRepertory.getBinaryReaderConverterByBsonType(currentBsonType).decode(bsonReader);
                }
                Utils.methodInvoke(method, collectionObject, javaValue);
            }
        }
        return collectionObject;
    }
}
