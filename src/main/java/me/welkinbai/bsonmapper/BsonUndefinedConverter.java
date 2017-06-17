package me.welkinbai.bsonmapper;

import me.welkinbai.bsonmapper.exception.BsonMapperConverterException;
import org.bson.BsonReader;
import org.bson.BsonUndefined;
import org.bson.BsonValue;
import org.bson.BsonWriter;

/**
 * Created by welkinbai on 2017/3/25.
 */
final class BsonUndefinedConverter extends AbstractBsonConverter<BsonUndefined, BsonUndefined> {

    private BsonUndefinedConverter() {
    }

    public static BsonUndefinedConverter getInstance() {
        return new BsonUndefinedConverter();
    }

    @Override
    public BsonUndefined decode(BsonValue bsonValue) {
        throw new BsonMapperConverterException("BsonUndefined type is not support");
    }

    @Override
    public BsonUndefined encode(Object object) {
        throw new BsonMapperConverterException("BsonUndefined type is not support");
    }

    @Override
    public BsonUndefined decode(BsonReader bsonReader) {
        throw new BsonMapperConverterException("BsonUndefined type is not support");
    }

    @Override
    public void encode(BsonWriter bsonWriter, BsonUndefined value) {
        throw new BsonMapperConverterException("BsonUndefined type is not support");
    }
}
