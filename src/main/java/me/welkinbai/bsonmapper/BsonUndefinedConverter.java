package me.welkinbai.bsonmapper;

import me.welkinbai.bsonmapper.exception.BsonMapperConverterException;
import org.bson.BsonUndefined;
import org.bson.BsonValue;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonUndefinedConverter implements BsonValueConverter<BsonUndefined>{

    private BsonUndefinedConverter() {
    }

    public static BsonUndefinedConverter getInstance() {
        return new BsonUndefinedConverter();
    }

    @Override
    public BsonUndefined decode(BsonValue bsonValue) {
        throw new BsonMapperConverterException("BsonUndefined type is not support");
    }
}
