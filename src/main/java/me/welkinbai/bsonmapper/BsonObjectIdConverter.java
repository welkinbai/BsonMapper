package me.welkinbai.bsonmapper;

import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.types.ObjectId;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonObjectIdConverter implements BsonValueConverter<ObjectId>, BsonReaderConverter<ObjectId> {

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
    public ObjectId decode(BsonReader binaryReader) {
        return binaryReader.readObjectId();
    }
}
