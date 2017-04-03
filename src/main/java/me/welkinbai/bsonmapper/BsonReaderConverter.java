package me.welkinbai.bsonmapper;

import org.bson.BsonReader;

/**
 * Created by welkinbai on 2017/3/26.
 */
public interface BsonReaderConverter<T> extends BsonConverter {

    T decode(BsonReader bsonBinaryReader);

}
