package me.welkinbai.bsonmapper;

import me.welkinbai.bsonmapper.exception.BsonMapperConverterException;
import org.bson.BsonDocument;
import org.bson.BsonJavaScriptWithScope;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.types.CodeWithScope;

/**
 * Created by welkinbai on 2017/3/25.
 */
final class BsonCodeWithScopeConverter extends AbstractBsonConverter<CodeWithScope, BsonJavaScriptWithScope> {

    private BsonCodeWithScopeConverter() {
    }

    public static BsonCodeWithScopeConverter getInstance() {
        return new BsonCodeWithScopeConverter();
    }

    @Override
    public CodeWithScope decode(BsonValue bsonValue) {
        BsonJavaScriptWithScope bsonJavaScriptWithScope = bsonValue.asJavaScriptWithScope();
        return new CodeWithScope(bsonJavaScriptWithScope.getCode(), Document.parse(bsonJavaScriptWithScope.getScope().toJson()));
    }

    @Override
    public BsonJavaScriptWithScope encode(CodeWithScope object) {
        return new BsonJavaScriptWithScope(object.getCode(), BsonDocument.parse(object.getScope().toJson()));
    }

    /**
     * JavaScriptWithScope seems a "live" Javascript function in a MongoDB which refers to variables which exist outside the function.
     * detail:https://stackoverflow.com/questions/39155290/what-is-javascript-with-scope-in-mongodb
     * still can't support convert this type because don't know how it is work.
     *
     * @param bsonReader
     * @return
     */
    @Override
    public CodeWithScope decode(BsonReader bsonReader) {
        throw new BsonMapperConverterException("could not convert to CodeWithScope when use BsonBinaryReader.BsonBinaryReader haven't gave us scope.");
    }

    @Override
    public void encode(BsonWriter bsonWriter, CodeWithScope value) {
        bsonWriter.writeJavaScriptWithScope(value.getCode());
    }

}
