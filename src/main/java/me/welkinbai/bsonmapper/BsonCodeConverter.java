package me.welkinbai.bsonmapper;

import org.bson.BsonJavaScript;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.types.Code;

/**
 * Created by welkinbai on 2017/3/25.
 */
final class BsonCodeConverter extends AbstractBsonConverter<Code, BsonJavaScript> {

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
    public BsonJavaScript encode(Code object) {
        return new BsonJavaScript(object.getCode());
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
