package me.welkinbai.bsonmapper;

import org.bson.BsonDecimal128;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.types.Decimal128;

import java.math.BigDecimal;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonBigDecimalConverter implements BsonValueConverter<BigDecimal, BsonDecimal128>, BsonByteIOConverter<BigDecimal> {

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
    public BsonDecimal128 encode(Object object) {
        return new BsonDecimal128(new Decimal128((BigDecimal) object));
    }

    @Override
    public BigDecimal decode(BsonReader bsonReader) {
        return bsonReader.readDecimal128().bigDecimalValue();
    }

    @Override
    public void encode(BsonWriter bsonWriter, BigDecimal value) {
        bsonWriter.writeDecimal128(new Decimal128(value));
    }
}
