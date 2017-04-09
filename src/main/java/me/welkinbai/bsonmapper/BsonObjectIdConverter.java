package me.welkinbai.bsonmapper;

import org.bson.BsonObjectId;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.types.ObjectId;

import java.lang.reflect.Field;

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
    public BsonObjectId encode(Field field, Object object) {
        Class<?> fieldType = field.getType();
        if (fieldType == String.class) {
            return new BsonObjectId(new ObjectId((String) Utils.getFieldValue(field, object)));
        }
        return new BsonObjectId((ObjectId) Utils.getFieldValue(field, object));
    }

    @Override
    public ObjectId decode(BsonReader binaryReader) {
        return binaryReader.readObjectId();
    }
}
