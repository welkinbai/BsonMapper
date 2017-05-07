package me.welkinbai.bsonmapper;

import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;

/**
 * Created by welkinbai on 2017/3/23.
 */
class BsonStringConverter implements BsonValueConverter<String, BsonString>, BsonByteIOConverter<String> {

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
    public BsonString encode(Object object) {
        return new BsonString((String) object);
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
