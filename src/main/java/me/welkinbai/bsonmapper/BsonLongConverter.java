package me.welkinbai.bsonmapper;

import org.bson.BsonValue;

/**
 * Created by welkinbai on 2017/3/25.
 */
public class BsonLongConverter implements BsonValueConverter<Long> {

    private BsonLongConverter() {
    }

    public static BsonLongConverter getInstance() {
        return new BsonLongConverter();
    }

    @Override
    public Long decode(BsonValue bsonValue) {
        return bsonValue.asInt64().getValue();
    }
}
