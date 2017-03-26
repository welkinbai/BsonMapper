package me.welkinbai.bsonmapper;

import org.bson.BsonBinaryReader;
import org.bson.BsonRegularExpression;
import org.bson.BsonValue;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonRegularExpressionConverter implements BsonValueConverter<BsonRegularExpression>, BsonBinaryReaderConverter<BsonRegularExpression> {

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
    public BsonRegularExpression decode(BsonBinaryReader bsonBinaryReader) {
        return bsonBinaryReader.readRegularExpression();
    }
}
