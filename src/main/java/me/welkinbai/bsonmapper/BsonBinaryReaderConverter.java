package me.welkinbai.bsonmapper;

import org.bson.BsonBinaryReader;

/**
 * Created by welkinbai on 2017/3/26.
 */
public interface BsonBinaryReaderConverter<T> extends BsonConverter {

    T decode(BsonBinaryReader bsonBinaryReader);

}
