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

import java.util.Date;
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
        CLASS_BSON_VALUE_CONVERTER_MAP.put(Binary.class, BsonBinaryConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(BsonUndefined.class, BsonUndefinedConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(ObjectId.class, BsonObjectIdConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(Boolean.class, BsonBooleanConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(Date.class, BsonDateConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(BsonRegularExpression.class, BsonRegularExpressionConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(BsonDbPointer.class, BsonDbPointerConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(Code.class, BsonCodeConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(CodeWithScope.class, BsonCodeWithScopeConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(Symbol.class, BsonSymbolConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(Integer.class, BsonIntegerConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(Long.class, BsonLongConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(BsonTimestamp.class, BsonTimestampConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(Decimal128.class, BsonBigDecimalConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(MinKey.class, BsonMinKeyConverter.getInstance());
        CLASS_BSON_VALUE_CONVERTER_MAP.put(MaxKey.class, BsonMaxKeyConverter.getInstance());
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

    public static boolean isValueSupportClazz(Class<?> targetClazz) {
        return CLASS_BSON_VALUE_CONVERTER_MAP.keySet().contains(targetClazz);
    }
}
