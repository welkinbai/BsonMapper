package me.welkinbai.bsonmapper;

import org.bson.BsonObjectId;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;

/**
 * Created by welkinbai on 2017/6/18.
 */
class BsonStringObjectIdConverter extends AbstractBsonConverter<StringObjectId, BsonObjectId> {

    private BsonStringObjectIdConverter() {
    }

    public static BsonStringObjectIdConverter getInstance() {
        return new BsonStringObjectIdConverter();
    }

    @Override
    public StringObjectId decode(BsonReader bsonReader) {
        return new StringObjectId(bsonReader.readObjectId());
    }

    @Override
    public void encode(BsonWriter bsonWriter, StringObjectId value) {
        bsonWriter.writeObjectId(value.getInnerObjectId());
    }

    @Override
    public StringObjectId decode(BsonValue bsonValue) {
        return new StringObjectId(bsonValue.asObjectId().getValue());
    }

    @Override
    public BsonObjectId encode(StringObjectId object) {
        return new BsonObjectId(object.getInnerObjectId());
    }
}
