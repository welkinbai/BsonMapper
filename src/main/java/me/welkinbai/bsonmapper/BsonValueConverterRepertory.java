package me.welkinbai.bsonmapper;

import me.welkinbai.bsonmapper.exception.BsonMapperConverterException;
import org.bson.BsonType;
import org.bson.codecs.BsonTypeClassMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by baixiaoxuan on 2017/3/23.
 */
public class BsonValueConverterRepertory {
    private final static Map<Class<?>, BsonValueConverter> CLASS_BSON_VALUE_CONVERTER_MAP = new HashMap<Class<?>, BsonValueConverter>();
    private final static BsonTypeClassMap BSON_TYPE_CLASS_MAP = new BsonTypeClassMap();
    private final static BsonDocumentConverter BSON_DOCUMENT_CONVERTER = BsonDocumentConverter.getInstance();
    private final static BsonArrayConverter BSON_ARRAY_CONVERTER = BsonArrayConverter.getInstance();

    private BsonValueConverterRepertory() {
    }

    static {
        CLASS_BSON_VALUE_CONVERTER_MAP.put(String.class, BsonStringConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(Double.class, BsonDoubleConverter.getInstance());
    }

    public static BsonValueConverter getConverterByBsonType(BsonType bsonType) {
        Class<?> clazz = BSON_TYPE_CLASS_MAP.get(bsonType);
        if (clazz == null) {
            throw new BsonMapperConverterException("can find BsonValueConverter for bsonType:" + bsonType);
        }
        return CLASS_BSON_VALUE_CONVERTER_MAP.get(clazz);
    }

    public static BsonDocumentConverter getBsonDocumentConverter() {
        return BSON_DOCUMENT_CONVERTER;
    }

    public static BsonArrayConverter getBsonArrayConverter() {
        return BSON_ARRAY_CONVERTER;
    }

    public static <T> boolean isValueSupportClazz(Class<T> targetClazz) {
        return CLASS_BSON_VALUE_CONVERTER_MAP.keySet().contains(targetClazz);
    }
}
