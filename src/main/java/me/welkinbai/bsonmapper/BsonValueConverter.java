package me.welkinbai.bsonmapper;

import org.bson.BsonValue;

import java.lang.reflect.Field;

/**
 * Created by welkinbai on 2017/3/23.
 */
public interface BsonValueConverter<T, V extends BsonValue> extends BsonConverter {

    T decode(BsonValue bsonValue);

    V encode(Field field, Object object);
}
