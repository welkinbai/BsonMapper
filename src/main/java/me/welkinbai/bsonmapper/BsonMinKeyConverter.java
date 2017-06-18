package me.welkinbai.bsonmapper;

import org.bson.BsonMinKey;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.types.MinKey;

/**
 * Created by welkinbai on 2017/3/25.
 */
final class BsonMinKeyConverter extends AbstractBsonConverter<MinKey, BsonMinKey> {

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
    public BsonMinKey encode(MinKey object) {
        return new BsonMinKey();
    }

    @Override
    public MinKey decode(BsonReader bsonReader) {
        return new MinKey();
    }

    @Override
    public void encode(BsonWriter bsonWriter, MinKey value) {
        bsonWriter.writeMinKey();
    }
}
