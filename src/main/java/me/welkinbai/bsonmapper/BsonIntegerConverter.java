package me.welkinbai.bsonmapper;

import org.bson.BsonInt32;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonIntegerConverter implements BsonValueConverter<Integer, BsonInt32>, BsonByteIOConverter<Integer> {

    private BsonIntegerConverter() {
    }

    public static BsonIntegerConverter getInstance() {
        return new BsonIntegerConverter();
    }

    @Override
    public Integer decode(BsonValue bsonValue) {
        return bsonValue.asInt32().getValue();
    }

    @Override
    public BsonInt32 encode(Object object) {
        return new BsonInt32((Integer) object);
    }

    @Override
    public Integer decode(BsonReader bsonReader) {
        return bsonReader.readInt32();
    }

    @Override
    public void encode(BsonWriter bsonWriter, Integer value) {
        bsonWriter.writeInt32(value);
    }
}
