package me.welkinbai.bsonmapper;

import org.bson.BsonInt32;
import org.bson.BsonReader;
import org.bson.BsonValue;

import java.lang.reflect.Field;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonIntegerConverter implements BsonValueConverter<Integer, BsonInt32>, BsonReaderConverter<Integer> {

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
    public BsonInt32 encode(Field field, Object object) {
        return new BsonInt32((Integer) Utils.getFieldValue(field, object));
    }

    @Override
    public Integer decode(BsonReader binaryReader) {
        return binaryReader.readInt32();
    }
}
