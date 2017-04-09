package me.welkinbai.bsonmapper;

import org.bson.BsonInt64;
import org.bson.BsonReader;
import org.bson.BsonValue;

import java.lang.reflect.Field;

/**
 * Created by welkinbai on 2017/3/25.
 */
public class BsonLongConverter implements BsonValueConverter<Long, BsonInt64>, BsonReaderConverter<Long> {

    private BsonLongConverter() {
    }

    public static BsonLongConverter getInstance() {
        return new BsonLongConverter();
    }

    @Override
    public Long decode(BsonValue bsonValue) {
        return bsonValue.asInt64().getValue();
    }

    @Override
    public BsonInt64 encode(Field field, Object object) {
        return new BsonInt64((Long) Utils.getFieldValue(field, object));
    }

    @Override
    public Long decode(BsonReader binaryReader) {
        return binaryReader.readInt64();
    }
}
