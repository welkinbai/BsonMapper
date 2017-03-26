package me.welkinbai.bsonmapper;

import org.bson.BsonBinaryReader;
import org.bson.BsonTimestamp;
import org.bson.BsonValue;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonTimestampConverter implements BsonValueConverter<BsonTimestamp>, BsonBinaryReaderConverter<BsonTimestamp> {

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
    public BsonTimestamp decode(BsonBinaryReader bsonBinaryReader) {
        return bsonBinaryReader.readTimestamp();
    }
}
