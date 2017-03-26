package me.welkinbai.bsonmapper;

import me.welkinbai.bsonmapper.exception.BsonMapperConverterException;
import org.bson.BsonBinaryReader;
import org.bson.BsonJavaScriptWithScope;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.types.CodeWithScope;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonCodeWithScopeConverter implements BsonValueConverter<CodeWithScope>, BsonBinaryReaderConverter<CodeWithScope> {

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
    public CodeWithScope decode(BsonBinaryReader bsonBinaryReader) {
        throw new BsonMapperConverterException("could not convert to CodeWithScope when use BsonBinaryReader.BsonBinaryReader haven't gave us scope.");
    }
}
