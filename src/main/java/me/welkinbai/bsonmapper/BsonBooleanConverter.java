package me.welkinbai.bsonmapper;

import org.bson.BsonBoolean;
import org.bson.BsonReader;
import org.bson.BsonValue;

import java.lang.reflect.Field;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonBooleanConverter implements BsonValueConverter<Boolean, BsonBoolean>, BsonReaderConverter<Boolean> {

    private BsonBooleanConverter() {
    }

    public static BsonBooleanConverter getInstance() {
        return new BsonBooleanConverter();
    }

    @Override
    public Boolean decode(BsonValue bsonValue) {
        return bsonValue.asBoolean().getValue();
    }

    @Override
    public BsonBoolean encode(Field field, Object object) {
        return new BsonBoolean((Boolean) Utils.getFieldValue(field, object));
    }

    @Override
    public Boolean decode(BsonReader bsonReader) {
        return bsonReader.readBoolean();
    }
}
