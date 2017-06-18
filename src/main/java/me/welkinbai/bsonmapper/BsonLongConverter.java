package me.welkinbai.bsonmapper;

import org.bson.BsonInt64;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;

/**
 * Created by welkinbai on 2017/3/25.
 */
final class BsonLongConverter extends AbstractBsonConverter<Long, BsonInt64> {

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
    public BsonInt64 encode(Long object) {
        return new BsonInt64(object);
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
