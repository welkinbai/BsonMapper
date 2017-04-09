package me.welkinbai.bsonmapper;

import org.bson.BsonDouble;
import org.bson.BsonReader;
import org.bson.BsonValue;

import java.lang.reflect.Field;

/**
 * Created by welkinbai on 2017/3/23.
 */
class BsonDoubleConverter implements BsonValueConverter<Double, BsonDouble>, BsonReaderConverter<Double> {

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
    public BsonDouble encode(Field field, Object object) {
        return new BsonDouble((Double) Utils.getFieldValue(field, object));
    }

    @Override
    public Double decode(BsonReader bsonReader) {
        return bsonReader.readDouble();
    }
}
