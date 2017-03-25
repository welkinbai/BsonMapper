package me.welkinbai.bsonmapper;

import org.bson.BsonValue;
import org.bson.types.MaxKey;

/**
 * Created by welkinbai on 2017/3/25.
 */
public class BsonMaxKeyConverter implements BsonValueConverter<MaxKey> {

    private BsonMaxKeyConverter() {
    }

    public static BsonMaxKeyConverter getInstance() {
        return new BsonMaxKeyConverter();
    }

    @Override
    public MaxKey decode(BsonValue bsonValue) {
        return new MaxKey();
    }
}
