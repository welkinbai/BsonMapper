package me.welkinbai.bsonmapper;

import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.types.MinKey;

/**
 * Created by welkinbai on 2017/3/25.
 */
public class BsonMinKeyConverter implements BsonValueConverter<MinKey>, BsonReaderConverter<MinKey> {

    private BsonMinKeyConverter() {
    }

    public static BsonMinKeyConverter getInstance() {
        return new BsonMinKeyConverter();
    }

    @Override
    public MinKey decode(BsonValue bsonValue) {
        return new MinKey();
    }

    @Override
    public MinKey decode(BsonReader bsonBinaryReader) {
        return new MinKey();
    }
}
