package me.welkinbai.bsonmapper;

import org.bson.BsonReader;
import org.bson.BsonWriter;

/**
 * Created by welkinbai on 2017/3/26.
 */
public interface BsonByteIOConverter<T> extends BsonConverter {

    T decode(BsonReader bsonReader);

    void encode(BsonWriter bsonWriter, T value);
}
