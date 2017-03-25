package me.welkinbai.bsonmapper;

import org.bson.BsonBinary;
import org.bson.BsonValue;
import org.bson.types.Binary;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonBinaryConverter implements BsonValueConverter<Binary> {
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
}
