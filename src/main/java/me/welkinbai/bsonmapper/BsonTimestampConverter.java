package me.welkinbai.bsonmapper;

import org.bson.BsonReader;
import org.bson.BsonTimestamp;
import org.bson.BsonValue;
import org.bson.BsonWriter;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonTimestampConverter implements BsonValueConverter<BsonTimestamp, BsonTimestamp>, BsonByteIOConverter<BsonTimestamp> {

    private BsonTimestampConverter() {
    }

    public static BsonTimestampConverter getInstance() {
        return new BsonTimestampConverter();
    }

    @Override
    public BsonTimestamp decode(BsonValue bsonValue) {
        return bsonValue.asTimestamp();
    }

    @Override
    public BsonTimestamp encode(Object object) {
        return (BsonTimestamp) object;
    }

    @Override
    public BsonTimestamp decode(BsonReader bsonReader) {
        return bsonReader.readTimestamp();
    }

    @Override
    public void encode(BsonWriter bsonWriter, BsonTimestamp value) {
        bsonWriter.writeTimestamp(value);
    }
}
