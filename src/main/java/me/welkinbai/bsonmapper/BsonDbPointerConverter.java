package me.welkinbai.bsonmapper;

import org.bson.BsonBinaryReader;
import org.bson.BsonDbPointer;
import org.bson.BsonValue;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonDbPointerConverter implements BsonValueConverter<BsonDbPointer>, BsonBinaryReaderConverter<BsonDbPointer> {

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
    public BsonDbPointer decode(BsonBinaryReader bsonBinaryReader) {
        return bsonBinaryReader.readDBPointer();
    }
}
