package me.welkinbai.bsonmapper;

import org.bson.BsonInt32;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;

/**
 * Created by welkinbai on 2017/3/25.
 */
final class BsonIntegerConverter extends AbstractBsonConverter<Integer, BsonInt32> {

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
    public BsonInt32 encode(Integer object) {
        return new BsonInt32(object);
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
