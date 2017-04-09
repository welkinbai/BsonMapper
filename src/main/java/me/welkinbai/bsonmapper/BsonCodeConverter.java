package me.welkinbai.bsonmapper;

import org.bson.BsonJavaScript;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.types.Code;

import java.lang.reflect.Field;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonCodeConverter implements BsonValueConverter<Code, BsonJavaScript>, BsonReaderConverter<Code> {

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
    public BsonJavaScript encode(Field field, Object object) {
        return new BsonJavaScript(((Code) Utils.getFieldValue(field, object)).getCode());
    }

    @Override
    public Code decode(BsonReader bsonReader) {
        return new Code(bsonReader.readJavaScript());
    }
}
