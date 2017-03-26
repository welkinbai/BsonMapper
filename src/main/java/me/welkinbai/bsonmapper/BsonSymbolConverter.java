package me.welkinbai.bsonmapper;

import org.bson.BsonBinaryReader;
import org.bson.BsonValue;
import org.bson.types.Symbol;

/**
 * Created by welkinbai on 2017/3/25.
 */
public class BsonSymbolConverter implements BsonValueConverter<Symbol>, BsonBinaryReaderConverter<Symbol> {

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
    public Symbol decode(BsonBinaryReader bsonBinaryReader) {
        return new Symbol(bsonBinaryReader.readSymbol());
    }
}
