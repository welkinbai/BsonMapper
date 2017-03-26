package me.welkinbai.bsonmapper;

import org.bson.BsonBinaryReader;
import org.bson.BsonValue;

import java.util.Date;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonDateConverter implements BsonValueConverter<Date>, BsonBinaryReaderConverter<Date> {

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
    public Date decode(BsonBinaryReader bsonBinaryReader) {
        return new Date(bsonBinaryReader.readDateTime());
    }
}
