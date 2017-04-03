package me.welkinbai.bsonmapper;

import org.bson.BsonReader;
import org.bson.BsonTimestamp;
import org.bson.BsonValue;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonTimestampConverter implements BsonValueConverter<BsonTimestamp>, BsonReaderConverter<BsonTimestamp> {

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
    public BsonTimestamp decode(BsonReader binaryReader) {
        return binaryReader.readTimestamp();
    }
}
