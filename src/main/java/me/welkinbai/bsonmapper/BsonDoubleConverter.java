package me.welkinbai.bsonmapper;

import org.bson.BsonReader;
import org.bson.BsonValue;

/**
 * Created by welkinbai on 2017/3/23.
 */
class BsonDoubleConverter implements BsonValueConverter<Double>, BsonReaderConverter<Double> {

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
    public Double decode(BsonReader bsonReader) {
        return bsonReader.readDouble();
    }
}
