package me.welkinbai.bsonmapper;

import org.bson.BsonBinary;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.types.Binary;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonBinaryConverter implements BsonValueConverter<Binary, BsonBinary>, BsonByteIOConverter<Binary> {
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
    public BsonBinary encode(Object object) {
        Binary value = ((Binary) object);
        return new BsonBinary(value.getType(), value.getData());
    }

    @Override
    public Binary decode(BsonReader bsonReader) {
        BsonBinary bsonBinary = bsonReader.readBinaryData();
        return new Binary(bsonBinary.getType(), bsonBinary.getData());
    }

    @Override
    public void encode(BsonWriter bsonWriter, Binary value) {
        bsonWriter.writeBinaryData(new BsonBinary(value.getType(), value.getData()));
    }
}
