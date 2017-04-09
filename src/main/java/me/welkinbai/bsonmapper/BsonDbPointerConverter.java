package me.welkinbai.bsonmapper;

import org.bson.BsonDbPointer;
import org.bson.BsonReader;
import org.bson.BsonValue;

import java.lang.reflect.Field;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonDbPointerConverter implements BsonValueConverter<BsonDbPointer, BsonDbPointer>, BsonReaderConverter<BsonDbPointer> {

    private BsonDbPointerConverter() {
    }

    public static BsonDbPointerConverter getInstance() {
        return new BsonDbPointerConverter();
    }

    @Override
    public BsonDbPointer decode(BsonValue bsonValue) {
        return bsonValue.asDBPointer();
    }

    @Override
    public BsonDbPointer encode(Field field, Object object) {
        return (BsonDbPointer) Utils.getFieldValue(field, object);
    }

    @Override
    public BsonDbPointer decode(BsonReader bsonReader) {
        return bsonReader.readDBPointer();
    }
}
