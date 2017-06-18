package me.welkinbai.bsonmapper;

import org.bson.BsonReader;
import org.bson.BsonRegularExpression;
import org.bson.BsonValue;
import org.bson.BsonWriter;

/**
 * Created by welkinbai on 2017/3/25.
 */
final class BsonRegularExpressionConverter extends AbstractBsonConverter<BsonRegularExpression, BsonRegularExpression> {

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
    public BsonRegularExpression encode(BsonRegularExpression object) {
        return object;
    }

    @Override
    public BsonRegularExpression decode(BsonReader bsonReader) {
        return bsonReader.readRegularExpression();
    }

    @Override
    public void encode(BsonWriter bsonWriter, BsonRegularExpression value) {
        bsonWriter.writeRegularExpression(value);
    }
}
