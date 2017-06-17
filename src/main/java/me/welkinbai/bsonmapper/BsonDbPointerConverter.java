package me.welkinbai.bsonmapper;

import org.bson.BsonDbPointer;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;

/**
 * Created by welkinbai on 2017/3/25.
 */
final class BsonDbPointerConverter extends AbstractBsonConverter<BsonDbPointer, BsonDbPointer> {

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
    public BsonDbPointer encode(Object object) {
        return (BsonDbPointer) object;
    }

    @Override
    public BsonDbPointer decode(BsonReader bsonReader) {
        return bsonReader.readDBPointer();
    }

    @Override
    public void encode(BsonWriter bsonWriter, BsonDbPointer value) {
        bsonWriter.writeDBPointer(value);
    }
}
