package me.welkinbai.bsonmapper;

import org.bson.BsonBoolean;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;

/**
 * Created by welkinbai on 2017/3/25.
 */
final class BsonBooleanConverter extends AbstractBsonConverter<Boolean, BsonBoolean> {

    private BsonBooleanConverter() {
    }

    public static BsonBooleanConverter getInstance() {
        return new BsonBooleanConverter();
    }

    @Override
    public Boolean decode(BsonValue bsonValue) {
        return bsonValue.asBoolean().getValue();
    }

    @Override
    public BsonBoolean encode(Boolean object) {
        return new BsonBoolean(object);
    }

    @Override
    public Boolean decode(BsonReader bsonReader) {
        return bsonReader.readBoolean();
    }

    @Override
    public void encode(BsonWriter bsonWriter, Boolean value) {
        bsonWriter.writeBoolean(value);
    }
}
