package me.welkinbai.bsonmapper;

import org.bson.BsonBinaryReader;
import org.bson.BsonValue;

/**
 * Created by welkinbai on 2017/3/25.
 */
public class BsonLongConverter implements BsonValueConverter<Long>, BsonBinaryReaderConverter<Long> {

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
    public Long decode(BsonBinaryReader bsonBinaryReader) {
        return bsonBinaryReader.readInt64();
    }
}
