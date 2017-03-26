package me.welkinbai.bsonmapper;

import org.bson.BsonBinaryReader;
import org.bson.BsonValue;
import org.bson.types.Code;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonCodeConverter implements BsonValueConverter<Code>, BsonBinaryReaderConverter<Code> {

    private BsonCodeConverter() {
    }

    public static BsonCodeConverter getInstance() {
        return new BsonCodeConverter();
    }

    @Override
    public Code decode(BsonValue bsonValue) {
        return new Code(bsonValue.asJavaScript().getCode());
    }

    @Override
    public Code decode(BsonBinaryReader bsonBinaryReader) {
        return new Code(bsonBinaryReader.readJavaScript());
    }
}
