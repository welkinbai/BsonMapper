package me.welkinbai.bsonmapper;

import org.bson.BsonDecimal128;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.types.Decimal128;

import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonBigDecimalConverter implements BsonValueConverter<BigDecimal, BsonDecimal128>, BsonReaderConverter<BigDecimal> {

    private BsonBigDecimalConverter() {
    }

    public static BsonBigDecimalConverter getInstance() {
        return new BsonBigDecimalConverter();
    }

    @Override
    public BigDecimal decode(BsonValue bsonValue) {
        return bsonValue.asDecimal128().decimal128Value().bigDecimalValue();
    }

    @Override
    public BsonDecimal128 encode(Field field, Object object) {
        return new BsonDecimal128(new Decimal128((BigDecimal) Utils.getFieldValue(field, object)));
    }

    @Override
    public BigDecimal decode(BsonReader bsonReader) {
        return bsonReader.readDecimal128().bigDecimalValue();
    }
}
