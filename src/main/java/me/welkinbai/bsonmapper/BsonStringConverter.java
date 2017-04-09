package me.welkinbai.bsonmapper;

import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;

import java.lang.reflect.Field;

import static me.welkinbai.bsonmapper.Utils.getFieldValue;

/**
 * Created by welkinbai on 2017/3/23.
 */
class BsonStringConverter implements BsonValueConverter<String, BsonString>, BsonReaderConverter<String> {

    private BsonStringConverter() {
    }

    public static BsonStringConverter getInstance() {
        return new BsonStringConverter();
    }

    @Override
    public String decode(BsonValue bsonValue) {
        return bsonValue.asString().getValue();
    }

    @Override
    public BsonString encode(Field field, Object object) {
        Object value = getFieldValue(field, object);
        return new BsonString((String) value);
    }

    @Override
    public String decode(BsonReader binaryReader) {
        return binaryReader.readString();
    }
}
