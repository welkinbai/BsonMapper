package me.welkinbai.bsonmapper;

import org.bson.BsonObjectId;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.types.ObjectId;

/**
 * Created by welkinbai on 2017/3/25.
 */
final class BsonObjectIdConverter extends AbstractBsonConverter<ObjectId, BsonObjectId> {

    private BsonObjectIdConverter() {
    }

    public static BsonObjectIdConverter getInstance() {
        return new BsonObjectIdConverter();
    }

    @Override
    public ObjectId decode(BsonValue bsonValue) {
        return bsonValue.asObjectId().getValue();
    }

    @Override
    public BsonObjectId encode(ObjectId object) {
        return new BsonObjectId(object);
    }

    @Override
    public ObjectId decode(BsonReader bsonReader) {
        return bsonReader.readObjectId();
    }

    @Override
    public void encode(BsonWriter bsonWriter, ObjectId value) {
        bsonWriter.writeObjectId(value);
    }
}
