package me.welkinbai.bsonmapper;

import org.bson.BsonBinaryReader;
import org.bson.BsonValue;

/**
 * Created by welkinbai on 2017/3/23.
 */
class BsonStringConverter implements BsonValueConverter<String>, BsonBinaryReaderConverter<String> {

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
    public String decode(BsonBinaryReader bsonBinaryReader) {
        return bsonBinaryReader.readString();
    }
}
