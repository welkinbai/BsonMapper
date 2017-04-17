package me.welkinbai.bsonmapper;

import org.bson.BsonMinKey;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.types.MinKey;

import java.lang.reflect.Field;

/**
 * Created by welkinbai on 2017/3/25.
 */
public class BsonMinKeyConverter implements BsonValueConverter<MinKey, BsonMinKey>, BsonReaderConverter<MinKey> {

    private BsonMinKeyConverter() {
    }

    public static BsonMinKeyConverter getInstance() {
        return new BsonMinKeyConverter();
    }

    @Override
    public MinKey decode(BsonValue bsonValue) {
        return new MinKey();
    }

    @Override
    public BsonMinKey encode(Field field, Object object) {
        return new BsonMinKey();
    }

    @Override
    public MinKey decode(BsonReader bsonBinaryReader) {
        return new MinKey();
    }
}
