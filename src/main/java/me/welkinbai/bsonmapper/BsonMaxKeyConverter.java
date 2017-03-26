package me.welkinbai.bsonmapper;

import org.bson.BsonBinaryReader;
import org.bson.BsonValue;
import org.bson.types.MaxKey;

/**
 * Created by welkinbai on 2017/3/25.
 */
public class BsonMaxKeyConverter implements BsonValueConverter<MaxKey>, BsonBinaryReaderConverter<MaxKey> {

    private BsonMaxKeyConverter() {
    }

    public static BsonMaxKeyConverter getInstance() {
        return new BsonMaxKeyConverter();
    }

    @Override
    public MaxKey decode(BsonValue bsonValue) {
        return new MaxKey();
    }

    @Override
    public MaxKey decode(BsonBinaryReader bsonBinaryReader) {
        return new MaxKey();
    }
}
