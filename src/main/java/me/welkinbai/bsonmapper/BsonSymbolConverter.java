package me.welkinbai.bsonmapper;

import org.bson.BsonReader;
import org.bson.BsonSymbol;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.types.Symbol;

/**
 * Created by welkinbai on 2017/3/25.
 */
final class BsonSymbolConverter implements BsonValueConverter<Symbol, BsonSymbol>, BsonByteIOConverter<Symbol> {

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
    public BsonSymbol encode(Object object) {
        return new BsonSymbol(((Symbol) object).getSymbol());
    }

    @Override
    public Symbol decode(BsonReader bsonReader) {
        return new Symbol(bsonReader.readSymbol());
    }

    @Override
    public void encode(BsonWriter bsonWriter, Symbol value) {
        bsonWriter.writeSymbol(value.getSymbol());
    }
}
