package me.welkinbai.bsonmapper;

import org.bson.BsonDocument;
import org.bson.io.BsonInput;
import org.bson.io.BsonOutput;

import java.nio.ByteBuffer;

/**
 * Created by welkinbai on 2017/3/21.
 */
public interface BsonMapper {

    <T> T readFrom(BsonDocument bsonDocument, Class<T> targetClazz);

    <T> T readFrom(ByteBuffer byteBuffer, Class<T> targetClazz);

    <T> T readFrom(BsonInput bsonInput, Class<T> targetClazz);

    <T> T readFrom(String jsonString, Class<T> targetClazz);

    BsonDocument writeToBsonDocument(Object object);

    BsonOutput writeToBsonOutput(Object object);

    String writeAsJsonStr(Object object);
}
