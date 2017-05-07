package me.welkinbai.bsonmapper;

import me.welkinbai.bsonmapper.exception.BsonMapperConverterException;
import org.bson.BsonDbPointer;
import org.bson.BsonRegularExpression;
import org.bson.BsonTimestamp;
import org.bson.BsonType;
import org.bson.BsonUndefined;
import org.bson.codecs.BsonTypeClassMap;
import org.bson.types.Binary;
import org.bson.types.Code;
import org.bson.types.CodeWithScope;
import org.bson.types.Decimal128;
import org.bson.types.MaxKey;
import org.bson.types.MinKey;
import org.bson.types.ObjectId;
import org.bson.types.Symbol;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by baixiaoxuan on 2017/3/23.
 */
public class BsonValueConverterRepertory {
    private final static Map<Class<?>, BsonConverter> CLASS_BSON_VALUE_CONVERTER_MAP = new HashMap<Class<?>, BsonConverter>();
    private final static BsonTypeClassMap BSON_TYPE_CLASS_MAP = new BsonTypeClassMap();
    private final static BsonDocumentConverter BSON_DOCUMENT_CONVERTER = BsonDocumentConverter.getInstance();
    private final static BsonArrayConverter BSON_ARRAY_CONVERTER = BsonArrayConverter.getInstance();

    private BsonValueConverterRepertory() {
    }

    static {
        CLASS_BSON_VALUE_CONVERTER_MAP.put(String.class, BsonStringConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(Double.class, BsonDoubleConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(double.class, BsonDoubleConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(Binary.class, BsonBinaryConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(BsonUndefined.class, BsonUndefinedConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(ObjectId.class, BsonObjectIdConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(Boolean.class, BsonBooleanConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(boolean.class, BsonBooleanConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(Date.class, BsonDateConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(BsonRegularExpression.class, BsonRegularExpressionConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(BsonDbPointer.class, BsonDbPointerConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(Code.class, BsonCodeConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(CodeWithScope.class, BsonCodeWithScopeConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(Symbol.class, BsonSymbolConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(Integer.class, BsonIntegerConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(int.class, BsonIntegerConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(Long.class, BsonLongConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(long.class, BsonLongConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(BsonTimestamp.class, BsonTimestampConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(Decimal128.class, BsonBigDecimalConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(MinKey.class, BsonMinKeyConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(MaxKey.class, BsonMaxKeyConverter.getInstance());
    }

    static BsonValueConverter getValueConverterByBsonType(BsonType bsonType) {
        Class<?> clazz = getClazzByBsonType(bsonType);
        return (BsonValueConverter) CLASS_BSON_VALUE_CONVERTER_MAP.get(clazz);
    }

    static BsonValueConverter getValueConverterByClazz(Class<?> clazz) {
        return (BsonValueConverter) CLASS_BSON_VALUE_CONVERTER_MAP.get(clazz);
    }

    static BsonByteIOConverter getByteIOConverterByBsonType(BsonType bsonType) {
        Class<?> clazz = getClazzByBsonType(bsonType);
        return (BsonByteIOConverter) CLASS_BSON_VALUE_CONVERTER_MAP.get(clazz);
    }

    static BsonByteIOConverter getByteIOConverterByClazz(Class<?> clazz) {
        return (BsonByteIOConverter) CLASS_BSON_VALUE_CONVERTER_MAP.get(clazz);
    }

    private static Class<?> getClazzByBsonType(BsonType bsonType) {
        Class<?> clazz = BSON_TYPE_CLASS_MAP.get(bsonType);
        if (clazz == null) {
            throw new BsonMapperConverterException("can not find BsonValueConverter for bsonType:" + bsonType);
        }
        return clazz;
    }

    static BsonDocumentConverter getBsonDocumentConverter() {
        return BSON_DOCUMENT_CONVERTER;
    }

    static BsonArrayConverter getBsonArrayConverter() {
        return BSON_ARRAY_CONVERTER;
    }

    public static boolean isValueSupportClazz(Class<?> targetClazz) {
        return CLASS_BSON_VALUE_CONVERTER_MAP.keySet().contains(targetClazz) || Collection.class.isAssignableFrom(targetClazz)
                || targetClazz.isArray();
    }

    public static boolean isCanConverterValueType(Class<?> targetClazz) {
        return CLASS_BSON_VALUE_CONVERTER_MAP.keySet().contains(targetClazz) || targetClazz.isPrimitive();
    }
}
