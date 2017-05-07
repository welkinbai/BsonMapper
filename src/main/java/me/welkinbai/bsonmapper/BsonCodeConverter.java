package me.welkinbai.bsonmapper;

import org.bson.BsonJavaScript;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.types.Code;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonCodeConverter implements BsonValueConverter<Code, BsonJavaScript>, BsonByteIOConverter<Code> {

    private BsonCodeConverter() {
    }

    public static BsonCodeConverter getInstance() {
        return new BsonCodeConverter();
    }

    @Override
    public Code decode(BsonValue bsonValue) {
        return new Code(bsonValue.asJavaScript().getCode());
    }

    @Override
    public BsonJavaScript encode(Object object) {
        return new BsonJavaScript(((Code) object).getCode());
    }

    @Override
    public Code decode(BsonReader bsonReader) {
        return new Code(bsonReader.readJavaScript());
    }

    @Override
    public void encode(BsonWriter bsonWriter, Code value) {
        bsonWriter.writeJavaScript(value.getCode());
    }
}
