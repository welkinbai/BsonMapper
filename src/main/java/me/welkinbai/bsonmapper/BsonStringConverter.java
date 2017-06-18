package me.welkinbai.bsonmapper;

import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;

/**
 * Created by welkinbai on 2017/3/23.
 */
final class BsonStringConverter extends AbstractBsonConverter<String, BsonString> {

    private BsonStringConverter() {
    }

    public static BsonStringConverter getInstance() {
        return new BsonStringConverter();
    }

    @Override
    public String decode(BsonValue bsonValue) {
        return bsonValue.asString().getValue();
    }

    @Override
    public BsonString encode(String object) {
        return new BsonString(object);
    }

    @Override
    public String decode(BsonReader bsonReader) {
        return bsonReader.readString();
    }

    @Override
    public void encode(BsonWriter bsonWriter, String value) {
        bsonWriter.writeString(value);
    }
}
