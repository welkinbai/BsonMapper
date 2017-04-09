package me.welkinbai.bsonmapper;

import org.bson.BsonReader;
import org.bson.BsonTimestamp;
import org.bson.BsonValue;

import java.lang.reflect.Field;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonTimestampConverter implements BsonValueConverter<BsonTimestamp, BsonTimestamp>, BsonReaderConverter<BsonTimestamp> {

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
    public BsonTimestamp encode(Field field, Object object) {
        return (BsonTimestamp) Utils.getFieldValue(field, object);
    }

    @Override
    public BsonTimestamp decode(BsonReader binaryReader) {
        return binaryReader.readTimestamp();
    }
}
