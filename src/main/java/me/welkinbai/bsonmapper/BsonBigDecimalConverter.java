package me.welkinbai.bsonmapper;

import org.bson.BsonValue;

import java.math.BigDecimal;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonBigDecimalConverter implements BsonValueConverter<BigDecimal> {

    private BsonBigDecimalConverter() {
    }

    public static BsonBigDecimalConverter getInstance() {
        return new BsonBigDecimalConverter();
    }

    @Override
    public BigDecimal decode(BsonValue bsonValue) {
        return bsonValue.asDecimal128().decimal128Value().bigDecimalValue();
    }
}
