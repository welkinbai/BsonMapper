package me.welkinbai.bsonmapper;

import org.bson.BsonObjectId;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.types.ObjectId;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonObjectIdConverter implements BsonValueConverter<ObjectId, BsonObjectId>, BsonReaderConverter<ObjectId> {

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
    public BsonObjectId encode(Object object) {
        Class<?> fieldType = object.getClass();
        if (fieldType == String.class) {
            return new BsonObjectId(new ObjectId((String) object));
        }
        return new BsonObjectId((ObjectId) object);
    }

    @Override
    public ObjectId decode(BsonReader binaryReader) {
        return binaryReader.readObjectId();
    }
}
