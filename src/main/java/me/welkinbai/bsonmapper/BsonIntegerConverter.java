package me.welkinbai.bsonmapper;

import org.bson.BsonReader;
import org.bson.BsonValue;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonIntegerConverter implements BsonValueConverter<Integer>, BsonReaderConverter<Integer> {

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
    public Integer decode(BsonReader binaryReader) {
        return binaryReader.readInt32();
    }
}
