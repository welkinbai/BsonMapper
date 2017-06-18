package me.welkinbai.bsonmapper;

import org.bson.BsonDateTime;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;

import java.util.Date;

/**
 * Created by welkinbai on 2017/3/25.
 */
final class BsonDateConverter extends AbstractBsonConverter<Date, BsonDateTime> {

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
    public BsonDateTime encode(Date object) {
        return new BsonDateTime(object.getTime());
    }

    @Override
    public Date decode(BsonReader bsonReader) {
        return new Date(bsonReader.readDateTime());
    }

    @Override
    public void encode(BsonWriter bsonWriter, Date value) {
        bsonWriter.writeDateTime(value.getTime());
    }
}
