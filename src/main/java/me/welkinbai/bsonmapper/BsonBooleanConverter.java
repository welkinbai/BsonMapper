package me.welkinbai.bsonmapper;

import org.bson.BsonValue;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonBooleanConverter implements BsonValueConverter<Boolean>{

    private BsonBooleanConverter() {
    }

    public static BsonBooleanConverter getInstance() {
        return new BsonBooleanConverter();
    }

    @Override
    public Boolean decode(BsonValue bsonValue) {
        return bsonValue.asBoolean().getValue();
    }
}
