package me.welkinbai.bsonmapper;

import org.bson.BsonValue;

import java.util.Date;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonDateConverter implements BsonValueConverter<Date>{

    private BsonDateConverter() {
    }

    public static BsonDateConverter getInstance() {
        return new BsonDateConverter();
    }

    @Override
    public Date decode(BsonValue bsonValue) {
        return new Date(bsonValue.asDateTime().getValue());
    }
}
