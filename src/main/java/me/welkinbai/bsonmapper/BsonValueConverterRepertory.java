package me.welkinbai.bsonmapper;

import org.bson.BsonType;
import org.bson.codecs.BsonTypeClassMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by baixiaoxuan on 2017/3/23.
 */
public class BsonValueConverterRepertory {
    private final static Map<Class<?>, BsonValueConverter> classBsonValueConverterMap = new HashMap<Class<?>, BsonValueConverter>();
    private final static BsonTypeClassMap bsonTypeClassMap = new BsonTypeClassMap();

    private BsonValueConverterRepertory() {
    }

    static {
        classBsonValueConverterMap.put(String.class, BsonStringConverter.getInstance());
        classBsonValueConverterMap.put(Double.class, BsonDoubleConverter.getInstance());
    }

    public static BsonValueConverter getConverterByBsonType(BsonType bsonType) {
        Class<?> clazz = bsonTypeClassMap.get(bsonType);
        if (clazz == null) {
            return null;
        }
        return classBsonValueConverterMap.get(clazz);
    }

}
