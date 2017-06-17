package me.welkinbai.bsonmapper;

import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;

/**
 * Created by welkinbai on 2017/6/17.
 */
public abstract class AbstractBsonConverter<T, V extends BsonValue> implements BsonValueConverter<T, V>, BsonByteIOConverter<T> {

    @Override
    public abstract T decode(BsonReader bsonReader);

    @Override
    public abstract void encode(BsonWriter bsonWriter, T value);

    @Override
    public abstract T decode(BsonValue bsonValue);

    @Override
    public abstract V encode(Object object);
}
