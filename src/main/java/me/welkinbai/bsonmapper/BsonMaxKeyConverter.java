package me.welkinbai.bsonmapper;

import org.bson.BsonMaxKey;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.types.MaxKey;

/**
 * Created by welkinbai on 2017/3/25.
 */
public class BsonMaxKeyConverter implements BsonValueConverter<MaxKey, BsonMaxKey>, BsonByteIOConverter<MaxKey> {

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
    public BsonMaxKey encode(Object object) {
        return new BsonMaxKey();
    }

    @Override
    public MaxKey decode(BsonReader bsonReader) {
        return new MaxKey();
    }

    @Override
    public void encode(BsonWriter bsonWriter, MaxKey value) {
        bsonWriter.writeMaxKey();
    }
}
