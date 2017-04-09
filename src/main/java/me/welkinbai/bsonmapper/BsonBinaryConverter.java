package me.welkinbai.bsonmapper;

import org.bson.BsonBinary;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.types.Binary;

import java.lang.reflect.Field;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonBinaryConverter implements BsonValueConverter<Binary, BsonBinary>, BsonReaderConverter<Binary> {
    private BsonBinaryConverter() {
    }

    public static BsonBinaryConverter getInstance() {
        return new BsonBinaryConverter();
    }


    @Override
    public Binary decode(BsonValue bsonValue) {
        BsonBinary bsonBinary = bsonValue.asBinary();
        return new Binary(bsonBinary.getType(), bsonBinary.getData());
    }

    @Override
    public BsonBinary encode(Field field, Object object) {
        Binary value = (Binary) Utils.getFieldValue(field, object);
        return new BsonBinary(value.getType(), value.getData());
    }

    @Override
    public Binary decode(BsonReader bsonReader) {
        BsonBinary bsonBinary = bsonReader.readBinaryData();
        return new Binary(bsonBinary.getType(), bsonBinary.getData());
    }
}
