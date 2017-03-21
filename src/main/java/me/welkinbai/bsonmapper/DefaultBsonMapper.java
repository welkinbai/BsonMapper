package me.welkinbai.bsonmapper;

import org.bson.BsonDocument;
import org.bson.BsonDocumentReader;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.io.BsonInput;

import java.nio.ByteBuffer;

/**
 * Created by welkinbai on 2017/3/21.
 */
public class DefaultBsonMapper implements BsonMapper {

    @Override
    public <T> T readFrom(BsonDocument bsonDocument, Class<T> targetClazz) {
        BsonReader bsonReader = new BsonDocumentReader(bsonDocument);
        bsonReader.readStartDocument();
        while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String name = bsonReader.readName();
            try {
                targetClazz.getMethod("set" + name);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public <T> T readFrom(ByteBuffer byteBuffer, Class<T> targetClazz) {
        return null;
    }

    @Override
    public <T> T readFrom(BsonInput bsonInput, Class<T> targetClazz) {
        return null;
    }

    @Override
    public <T> T readFrom(String jsonString, Class<T> targetClazz) {
        return null;
    }
}
