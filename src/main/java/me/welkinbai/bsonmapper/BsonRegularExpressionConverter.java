package me.welkinbai.bsonmapper;

import org.bson.BsonReader;
import org.bson.BsonRegularExpression;
import org.bson.BsonValue;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonRegularExpressionConverter implements BsonValueConverter<BsonRegularExpression, BsonRegularExpression>, BsonReaderConverter<BsonRegularExpression> {

    private BsonRegularExpressionConverter() {
    }

    public static BsonRegularExpressionConverter getInstance() {
        return new BsonRegularExpressionConverter();
    }

    @Override
    public BsonRegularExpression decode(BsonValue bsonValue) {
        return bsonValue.asRegularExpression();
    }

    @Override
    public BsonRegularExpression encode(Object object) {
        return (BsonRegularExpression) object;
    }

    @Override
    public BsonRegularExpression decode(BsonReader binaryReader) {
        return binaryReader.readRegularExpression();
    }
}
