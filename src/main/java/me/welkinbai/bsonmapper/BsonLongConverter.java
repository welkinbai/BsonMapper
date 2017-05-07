package me.welkinbai.bsonmapper;

import org.bson.BsonInt64;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;

/**
 * Created by welkinbai on 2017/3/25.
 */
public class BsonLongConverter implements BsonValueConverter<Long, BsonInt64>, BsonByteIOConverter<Long> {

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
    public BsonInt64 encode(Object object) {
        return new BsonInt64((Long) object);
    }

    @Override
    public Long decode(BsonReader bsonReader) {
        return bsonReader.readInt64();
    }

    @Override
    public void encode(BsonWriter bsonWriter, Long value) {
        bsonWriter.writeInt64(value);
    }
}
