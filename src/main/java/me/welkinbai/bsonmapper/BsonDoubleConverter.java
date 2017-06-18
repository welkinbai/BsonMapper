package me.welkinbai.bsonmapper;

import org.bson.BsonDouble;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;

/**
 * Created by welkinbai on 2017/3/23.
 */
final class BsonDoubleConverter extends AbstractBsonConverter<Double, BsonDouble> {

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
    public BsonDouble encode(Double object) {
        return new BsonDouble(object);
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
