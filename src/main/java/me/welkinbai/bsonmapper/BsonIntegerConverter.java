package me.welkinbai.bsonmapper;

import org.bson.BsonBinaryReader;
import org.bson.BsonValue;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonIntegerConverter implements BsonValueConverter<Integer>, BsonBinaryReaderConverter<Integer> {

    private BsonIntegerConverter() {
    }

    public static BsonIntegerConverter getInstance() {
        return new BsonIntegerConverter();
    }

    @Override
    public Integer decode(BsonValue bsonValue) {
        return bsonValue.asInt32().getValue();
    }

    @Override
    public Integer decode(BsonBinaryReader bsonBinaryReader) {
        return bsonBinaryReader.readInt32();
    }
}
