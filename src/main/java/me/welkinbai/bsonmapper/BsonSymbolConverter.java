package me.welkinbai.bsonmapper;

import org.bson.BsonReader;
import org.bson.BsonSymbol;
import org.bson.BsonValue;
import org.bson.types.Symbol;

import java.lang.reflect.Field;

/**
 * Created by welkinbai on 2017/3/25.
 */
class BsonSymbolConverter implements BsonValueConverter<Symbol, BsonSymbol>, BsonReaderConverter<Symbol> {

    private BsonSymbolConverter() {
    }

    public static BsonSymbolConverter getInstance() {
        return new BsonSymbolConverter();
    }

    @Override
    public Symbol decode(BsonValue bsonValue) {
        return new Symbol(bsonValue.asSymbol().getSymbol());
    }

    @Override
    public BsonSymbol encode(Field field, Object object) {
        return new BsonSymbol(((Symbol) Utils.getFieldValue(field, object)).getSymbol());
    }

    @Override
    public Symbol decode(BsonReader bsonReader) {
        return new Symbol(bsonReader.readSymbol());
    }
}
