package me.welkinbai.bsonmapper;

import org.bson.BsonValue;

/**
 * Created by welkinbai on 2017/3/23.
 */
public interface BsonValueConverter<T> {
    T decode(BsonValue bsonValue);
}
