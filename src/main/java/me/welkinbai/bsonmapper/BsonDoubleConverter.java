package me.welkinbai.bsonmapper;

import org.bson.BsonBinaryReader;
import org.bson.BsonValue;

/**
 * Created by welkinbai on 2017/3/23.
 */
class BsonDoubleConverter implements BsonValueConverter<Double>, BsonBinaryReaderConverter<Double> {

    private BsonDoubleConverter() {
    }

    public static BsonDoubleConverter getInstance() {
        return new BsonDoubleConverter();
    }

    @Override
    public Double decode(BsonValue bsonValue) {
        return bsonValue.asDouble().getValue();
    }

    @Override
    public Double decode(BsonBinaryReader bsonBinaryReader) {
        return bsonBinaryReader.readDouble();
    }
}
