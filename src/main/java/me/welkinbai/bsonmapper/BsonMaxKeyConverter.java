package me.welkinbai.bsonmapper;

import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.types.MaxKey;

import java.lang.reflect.Field;

/**
 * Created by welkinbai on 2017/3/25.
 */
public class BsonMaxKeyConverter implements BsonValueConverter<MaxKey, MaxKey>, BsonReaderConverter<MaxKey> {

    private BsonMaxKeyConverter() {
    }

    public static BsonMaxKeyConverter getInstance() {
        return new BsonMaxKeyConverter();
    }

    @Override
    public MaxKey decode(BsonValue bsonValue) {
        return new MaxKey();
    }

    @Override
    public MaxKey encode(Field field, Object object) {
        return (MaxKey) Utils.getFieldValue(field, object);
    }

    @Override
    public MaxKey decode(BsonReader bsonBinaryReader) {
        return new MaxKey();
    }
}
