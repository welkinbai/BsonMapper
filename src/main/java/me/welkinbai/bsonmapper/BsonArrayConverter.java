package me.welkinbai.bsonmapper;

import me.welkinbai.bsonmapper.annotations.BsonArrayField;
import me.welkinbai.bsonmapper.exception.BsonMapperConverterException;
import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonValue;
import org.bson.BsonWriter;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static me.welkinbai.bsonmapper.MapperLayerCounter.MAPPER_LAYER_COUNTER;

/**
 * Created by welkinbai on 2017/3/23.
 */
final class BsonArrayConverter {

    private BsonArrayConverter() {
    }

    static BsonArrayConverter getInstance() {
        return new BsonArrayConverter();
    }

    Object decode(BsonArray bsonArray, Field field, BsonMapperConfig bsonMapperConfig) {
        MAPPER_LAYER_COUNTER.addCount(bsonMapperConfig);
        try {
            Class<?> fieldType = field.getType();
            if (fieldType.isArray()) {
                return handleArrayForBsonArray(bsonArray, field, bsonMapperConfig);
            } else if (Collection.class.isAssignableFrom(fieldType)) {
                return handleCollectionForBsonArray(bsonArray, field, bsonMapperConfig);
            } else {
                throw new BsonMapperConverterException(
                        String.format("field %s should be array or Collection because there is a BsonArray in BsonDocument,BsonName is %s",
                                field.getName(), Utils.getBsonName(field)));
            }
        } finally {
            MAPPER_LAYER_COUNTER.reduceCount();
        }
    }

    private Object handleArrayForBsonArray(BsonArray bsonArray, Field field, BsonMapperConfig bsonMapperConfig) {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        Class<?> fieldClazz = field.getType();
        for (BsonValue bsonValue : bsonArray) {
            if (bsonValue == null) {
                continue;
            }
            if (bsonValue.isArray()) {
                arrayList.add(decode(bsonValue.asArray(), field, bsonMapperConfig));
            } else {
                Object javaValue;
                if (bsonValue.isDocument()) {
                    javaValue = BsonValueConverterRepertory.getBsonDocumentConverter().decode(bsonValue.asDocument(), fieldClazz.getComponentType(), bsonMapperConfig);
                } else {
                    javaValue = BsonValueConverterRepertory.getValueConverterByBsonType(bsonValue.getBsonType()).decode(bsonValue);
                }
                arrayList.add(javaValue);
            }
        }
        return arrayList.toArray((Object[]) Array.newInstance(fieldClazz.getComponentType(), 0));
    }


    Object handleCollectionForBsonArray(BsonArray bsonArray, Field field, BsonMapperConfig bsonMapperConfig) {
        Class<?> fieldType = field.getType();
        Class<?> implClass = Utils.giveImplClassIfSupport(fieldType);
        if (Utils.isUnableAddCollectionClazz(implClass)) {
            return null;
        }
        BsonArrayField bsonArrayField = Utils.getBsonArrayFieldAnnotation(field);
        Class<?> targetComponentClazz = bsonArrayField.componentType();

        Object collectionObject = Utils.newInstanceByClazz(implClass);
        Method method;
        try {
            method = implClass.getMethod("add", Object.class);
        } catch (NoSuchMethodException e) {
            throw new BsonMapperConverterException("NoSuchMethodException", e);
        }

        for (BsonValue bsonValue : bsonArray) {
            if (bsonValue == null) {
                continue;
            }
            if (bsonValue.isArray()) {
                BsonArray array = bsonValue.asArray();
                Utils.methodInvoke(method, collectionObject, decode(array, field, bsonMapperConfig));
            } else {
                Object javaValue;
                if (bsonValue.isDocument()) {
                    javaValue = BsonValueConverterRepertory.getBsonDocumentConverter().decode(bsonValue.asDocument(), targetComponentClazz, bsonMapperConfig);
                } else {
                    javaValue = BsonValueConverterRepertory.getValueConverterByBsonType(bsonValue.getBsonType()).decode(bsonValue);
                }
                Utils.methodInvoke(method, collectionObject, javaValue);
            }
        }
        return collectionObject;
    }

    Object decode(BsonReader bsonReader, Field field, BsonMapperConfig bsonMapperConfig) {
        MAPPER_LAYER_COUNTER.addCount(bsonMapperConfig);
        try {
            Class<?> fieldType = field.getType();
            if (fieldType.isArray()) {
                return handleArrayForBsonReader(bsonReader, field, bsonMapperConfig);
            } else if (Collection.class.isAssignableFrom(fieldType)) {
                return handleCollectionForBsonReader(bsonReader, field, bsonMapperConfig);
            } else {
                throw new BsonMapperConverterException(String.format("field %s should be array or Collection because there is a BsonArray in BsonDocument,BsonName is %s", field.getName(), Utils.getBsonName(field)));
            }
        } finally {
            MAPPER_LAYER_COUNTER.reduceCount();
        }
    }

    private Object handleArrayForBsonReader(BsonReader bsonReader, Field field, BsonMapperConfig bsonMapperConfig) {
        Class<?> fieldType = field.getType();
        ArrayList<Object> arrayList = new ArrayList<Object>();
        bsonReader.readStartArray();
        while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            BsonType currentBsonType = bsonReader.getCurrentBsonType();
            if (currentBsonType == BsonType.NULL) {
                continue;
            }
            if (currentBsonType == BsonType.ARRAY) {
                arrayList.add(decode(bsonReader, field, bsonMapperConfig));
            } else {
                Object javaValue;
                if (currentBsonType == BsonType.DOCUMENT) {
                    javaValue = BsonValueConverterRepertory.getBsonDocumentConverter().decode(bsonReader, fieldType.getComponentType(), bsonMapperConfig);
                } else {
                    javaValue = BsonValueConverterRepertory.getByteIOConverterByBsonType(currentBsonType).decode(bsonReader);
                }
                arrayList.add(javaValue);
            }
        }
        bsonReader.readEndArray();
        return arrayList.toArray((Object[]) Array.newInstance(fieldType.getComponentType(), 0));
    }

    Object handleCollectionForBsonReader(BsonReader bsonReader, Field field, BsonMapperConfig bsonMapperConfig) {
        Class<?> fieldType = field.getType();
        Class<?> implClass = Utils.giveImplClassIfSupport(fieldType);
        if (Utils.isUnableAddCollectionClazz(implClass)) {
            return null;
        }
        BsonArrayField bsonArrayField = Utils.getBsonArrayFieldAnnotation(field);
        Class<?> targetComponentClazz = bsonArrayField.componentType();

        Object collectionObject = Utils.newInstanceByClazz(fieldType);
        Method method;
        try {
            method = fieldType.getMethod("add", Object.class);
        } catch (NoSuchMethodException e) {
            throw new BsonMapperConverterException("NoSuchMethodException", e);
        }

        bsonReader.readStartArray();
        while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            BsonType currentBsonType = bsonReader.getCurrentBsonType();
            if (currentBsonType == BsonType.NULL) {
                continue;
            }
            if (currentBsonType == BsonType.ARRAY) {
                Utils.methodInvoke(method, collectionObject, decode(bsonReader, field, bsonMapperConfig));
            } else {
                Object javaValue;
                if (currentBsonType == BsonType.DOCUMENT) {
                    javaValue = BsonValueConverterRepertory.getBsonDocumentConverter().decode(bsonReader, targetComponentClazz, bsonMapperConfig);
                } else {
                    javaValue = BsonValueConverterRepertory.getByteIOConverterByBsonType(currentBsonType).decode(bsonReader);
                }
                Utils.methodInvoke(method, collectionObject, javaValue);
            }
        }
        bsonReader.readEndArray();
        return collectionObject;
    }

    public void encode(BsonArray bsonArray, Field field, Object fieldValue, BsonMapperConfig bsonMapperConfig) {
        MAPPER_LAYER_COUNTER.addCount(bsonMapperConfig);
        try {
            Class<?> fieldType = field.getType();
            String bsonName = Utils.getBsonName(field);
            if (fieldType.isArray()) {
                handleArrayForEncodeDocument(bsonArray, field, (Object[]) fieldValue, bsonMapperConfig);
            } else if (Collection.class.isAssignableFrom(fieldType)) {
                handleCollectionForEncodeDocument(bsonArray, field, (Collection) fieldValue, bsonMapperConfig);
            } else {
                throw new BsonMapperConverterException("exception should be never happen.the field:" + bsonName + "should be array or Collection");
            }
        } finally {
            MAPPER_LAYER_COUNTER.reduceCount();
        }
    }

    private void handleArrayForEncodeDocument(BsonArray bsonArray, Field field, Object[] fieldValue, BsonMapperConfig bsonMapperConfig) {
        Class<?> fieldType = field.getType();
        Class<?> componentType = fieldType.getComponentType();
        ArrayList<BsonValue> arrayList = getBsonValueList(field, Arrays.asList(fieldValue), bsonMapperConfig, componentType);
        bsonArray.addAll(arrayList);
    }

    private void handleCollectionForEncodeDocument(BsonArray bsonArray, Field field, Collection fieldValue, BsonMapperConfig bsonMapperConfig) {
        BsonArrayField fieldAnnotation = Utils.getBsonArrayFieldAnnotation(field);
        Class<?> componentType = fieldAnnotation.componentType();
        ArrayList<BsonValue> arrayList = getBsonValueList(field, fieldValue, bsonMapperConfig, componentType);
        bsonArray.addAll(arrayList);
    }

    private ArrayList<BsonValue> getBsonValueList(Field field, Collection values, BsonMapperConfig bsonMapperConfig, Class<?> componentType) {
        ArrayList<BsonValue> arrayList = new ArrayList<BsonValue>();
        for (Object o : values) {
            if (o == null) {
                continue;
            }
            Class<?> oClazz = o.getClass();
            if (Utils.isArrayType(oClazz)) {
                BsonArray innerBsonArray = new BsonArray();
                encode(innerBsonArray, field, o, bsonMapperConfig);
                arrayList.add(innerBsonArray);
            }
            if (componentType.isInstance(o)) {
                if (BsonValueConverterRepertory.isCanConverterValueType(componentType)) {
                    arrayList.add(BsonValueConverterRepertory.getValueConverterByClazz(componentType).encode(o));
                } else {
                    BsonDocument arrayEle = new BsonDocument();
                    BsonValueConverterRepertory.getBsonDocumentConverter().encode(arrayEle, o, bsonMapperConfig);
                    arrayList.add(arrayEle);
                }
            } else {
                throw new BsonMapperConverterException(String.format("array field has element which has different type with declaring componentType.field name: %s", field.getName()));
            }
        }
        return arrayList;
    }

    public void encode(BsonWriter bsonWriter, Field field, Object fieldValue) {
        bsonWriter.writeStartArray();
        Class<?> fieldType = field.getType();
        if (fieldType.isArray()) {
            handleArrayForEncodeWriter(bsonWriter, field, (Object[]) fieldValue);
        } else if (Collection.class.isAssignableFrom(fieldType)) {
            handleCollectionForEncodeWriter(bsonWriter, field, (Collection) fieldValue);
        } else {
            throw new BsonMapperConverterException("exception should be never happen.the field:" + Utils.getBsonName(field) + "should be array or Collection");
        }
        bsonWriter.writeEndArray();
    }

    private void handleArrayForEncodeWriter(BsonWriter bsonWriter, Field field, Object[] fieldValue) {
        Class<?> fieldType = field.getType();
        Class<?> componentType = fieldType.getComponentType();
        writeValuesToBson(bsonWriter, field, Arrays.asList(fieldValue), componentType);
    }

    private void handleCollectionForEncodeWriter(BsonWriter bsonWriter, Field field, Collection fieldValue) {
        BsonArrayField fieldAnnotation = Utils.getBsonArrayFieldAnnotation(field);
        Class<?> componentType = fieldAnnotation.componentType();
        writeValuesToBson(bsonWriter, field, fieldValue, componentType);
    }

    private void writeValuesToBson(BsonWriter bsonWriter, Field field, Collection fieldValue, Class<?> componentType) {
        for (Object value : fieldValue) {
            if (value == null) {
                continue;
            }
            Class<?> valueClazz = value.getClass();
            if (Utils.isArrayType(valueClazz)) {
                encode(bsonWriter, field, value);
                continue;
            }
            if (componentType.isInstance(value)) {
                if (BsonValueConverterRepertory.isCanConverterValueType(componentType)) {
                    BsonValueConverterRepertory.getByteIOConverterByClazz(componentType).encode(bsonWriter, value);
                } else {
                    BsonValueConverterRepertory.getBsonDocumentConverter().encode(bsonWriter, value);
                }
            } else {
                throw new BsonMapperConverterException(String.format("array field has element which has different type with declaring componentType.field name: %s", field.getName()));
            }
        }
    }

}
