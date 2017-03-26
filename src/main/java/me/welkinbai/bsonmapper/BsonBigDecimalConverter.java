package me.welkinbai.bsonmapper;

import org.bson.BsonBinaryReader;
import org.bson.BsonValue;

import java.math.BigDecimal;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonBigDecimalConverter implements BsonValueConverter<BigDecimal>, BsonBinaryReaderConverter<BigDecimal> {

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
    public BigDecimal decode(BsonBinaryReader bsonBinaryReader) {
        return bsonBinaryReader.doReadDecimal128().bigDecimalValue();
    }
}
