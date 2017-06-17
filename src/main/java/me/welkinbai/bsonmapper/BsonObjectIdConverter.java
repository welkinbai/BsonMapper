package me.welkinbai.bsonmapper;

import me.welkinbai.bsonmapper.exception.BsonMapperConverterException;
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
    public BsonObjectId encode(Object object) {
        Class<?> fieldType = object.getClass();
        if (fieldType == String.class) {
            return new BsonObjectId(new ObjectId((String) object));
        } else if (fieldType == ObjectId.class) {
            return new BsonObjectId((ObjectId) object);
        }
        throw new BsonMapperConverterException("can not convert objectId.because unsupported field type:" + fieldType.getName());
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
