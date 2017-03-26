package me.welkinbai.bsonmapper;

import org.bson.BsonBinaryReader;
import org.bson.BsonValue;
import org.bson.types.ObjectId;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonObjectIdConverter implements BsonValueConverter<ObjectId>, BsonBinaryReaderConverter<ObjectId> {

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
    public ObjectId decode(BsonBinaryReader bsonBinaryReader) {
        return bsonBinaryReader.readObjectId();
    }
}
