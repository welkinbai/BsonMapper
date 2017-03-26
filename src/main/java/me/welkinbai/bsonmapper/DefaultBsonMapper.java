package me.welkinbai.bsonmapper;

import org.bson.BsonDocument;
import org.bson.io.BsonInput;

import java.nio.ByteBuffer;

import static me.welkinbai.bsonmapper.Utils.checkNotNull;

/**
 * Created by welkinbai on 2017/3/21.
 */
public class DefaultBsonMapper implements BsonMapper {

    @Override
    public <T> T readFrom(BsonDocument bsonDocument, Class<T> targetClazz) {
        checkNotNull(targetClazz, "targetClazz should not be Null!");
        return BsonValueConverterRepertory.getBsonDocumentConverter().decode(bsonDocument, targetClazz);
    }

    @Override
    public <T> T readFrom(ByteBuffer byteBuffer, Class<T> targetClazz) {
        checkNotNull(targetClazz, "targetClazz should not be Null!");
        return BsonValueConverterRepertory.getBsonDocumentConverter().decode(byteBuffer, targetClazz);
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
