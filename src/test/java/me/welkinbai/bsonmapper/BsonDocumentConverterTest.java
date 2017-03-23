package me.welkinbai.bsonmapper;

import me.welkinbai.bsonmapper.TestPOJO.BsonTest;
import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonDouble;
import org.bson.BsonString;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by welkinbai on 2017/3/23.
 */
public class BsonDocumentConverterTest {

    @Test
    public void decode() throws Exception {
        List<BsonString> list = new ArrayList<BsonString>();
        list.add(new BsonString("testArrayV"));
        list.add(new BsonString("testArrayV2"));
        List<BsonArray> arrayList = new ArrayList<BsonArray>();
        arrayList.add(new BsonArray(list));
        arrayList.add(new BsonArray(list));
        BsonDocument bsonDocument = new BsonDocument().append("testDouble", new BsonDouble(20.99))
                .append("testString", new BsonString("testStringV"))
                .append("testArray", new BsonArray(arrayList));
        BsonDocument bsonDocument1 = new BsonDocument().append("testDouble", new BsonDouble(20.99))
                .append("testString", new BsonString("testStringV"))
                .append("testArray", new BsonArray(arrayList))
                .append("bson_test", bsonDocument);
        BsonTest bsonTest = BsonDocumentConverter.getInstance().decode(bsonDocument1, BsonTest.class);
        System.out.println(bsonTest.getTestDouble());
        System.out.println(bsonTest.getTestString());
        System.out.println(Arrays.deepToString(bsonTest.getTestArray()));
        System.out.println(bsonTest);
    }

}