package me.welkinbai.bsonmapper;

import org.bson.BsonDateTime;
import org.bson.BsonReader;
import org.bson.BsonValue;

import java.util.Date;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonDateConverter implements BsonValueConverter<Date, BsonDateTime>, BsonReaderConverter<Date> {

    private BsonDateConverter() {
    }

    public static BsonDateConverter getInstance() {
        return new BsonDateConverter();
    }

    @Override
    public Date decode(BsonValue bsonValue) {
        return new Date(bsonValue.asDateTime().getValue());
    }

    @Override
    public BsonDateTime encode(Object object) {
        return new BsonDateTime(((Date) object).getTime());
    }

    @Override
    public Date decode(BsonReader bsonReader) {
        return new Date(bsonReader.readDateTime());
    }
}
