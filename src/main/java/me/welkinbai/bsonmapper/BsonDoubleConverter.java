package me.welkinbai.bsonmapper;

import org.bson.BsonDouble;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;

/**
 * Created by welkinbai on 2017/3/23.
 */
final class BsonDoubleConverter implements BsonValueConverter<Double, BsonDouble>, BsonByteIOConverter<Double> {

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
    public BsonDouble encode(Object object) {
        return new BsonDouble((Double) object);
    }

    @Override
    public Double decode(BsonReader bsonReader) {
        return bsonReader.readDouble();
    }

    @Override
    public void encode(BsonWriter bsonWriter, Double value) {
        bsonWriter.writeDouble(value);
    }
}
