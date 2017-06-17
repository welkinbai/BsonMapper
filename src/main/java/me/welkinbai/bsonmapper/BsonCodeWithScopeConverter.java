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
final class BsonCodeWithScopeConverter implements BsonValueConverter<CodeWithScope, BsonJavaScriptWithScope>, BsonByteIOConverter<CodeWithScope> {

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
    public BsonJavaScriptWithScope encode(Object object) {
        CodeWithScope value = (CodeWithScope) object;
        return new BsonJavaScriptWithScope(value.getCode(), BsonDocument.parse(value.getScope().toJson()));
    }

    @Override
    public CodeWithScope decode(BsonReader bsonReader) {
//todo_welkin 研究一下javaScriptWithScope是什么类型
        throw new BsonMapperConverterException("could not convert to CodeWithScope when use BsonBinaryReader.BsonBinaryReader haven't gave us scope.");
    }

    @Override
    public void encode(BsonWriter bsonWriter, CodeWithScope value) {
        bsonWriter.writeJavaScriptWithScope(value.getCode());
    }

}
