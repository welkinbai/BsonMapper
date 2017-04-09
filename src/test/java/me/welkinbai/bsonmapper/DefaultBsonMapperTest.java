package me.welkinbai.bsonmapper;

import me.welkinbai.bsonmapper.TestPOJO.BsonTest;
import org.bson.BsonArray;
import org.bson.BsonBinary;
import org.bson.BsonBoolean;
import org.bson.BsonDateTime;
import org.bson.BsonDocument;
import org.bson.BsonDocumentReader;
import org.bson.BsonDouble;
import org.bson.BsonInt32;
import org.bson.BsonInt64;
import org.bson.BsonNull;
import org.bson.BsonObjectId;
import org.bson.BsonString;
import org.bson.BsonUndefined;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by welkinbai on 2017/4/4.
 */
public class DefaultBsonMapperTest {


    @Test
    public void readFrom() throws Exception {
        BsonDocument bsonDocument1 = getBsonDocument();
        BsonMapper bsonMapper = DefaultBsonMapper.defaultBsonMapper();
        BsonTest bsonTest = bsonMapper.readFrom(bsonDocument1, BsonTest.class);
        System.out.println(bsonTest.getTestDouble());
        System.out.println(bsonTest.getTestString());
        System.out.println(bsonTest.getTestArray());
        System.out.println(Arrays.toString(bsonTest.getTestBinary().getData()));
        System.out.println(bsonTest.getTestObjectId());
        System.out.println(bsonTest.getTestStringObjectId());
        System.out.println(bsonTest.isTestBoolean());
        System.out.println(bsonTest.getTestDate());
        System.out.println(bsonTest.getTestNull());
        System.out.println(bsonTest.getTestInt());
        System.out.println(bsonTest.getTestLong());
        System.out.println(bsonTest);
    }

    private BsonDocument getBsonDocument() {
        BsonDocument bsonObj = new BsonDocument().append("testDouble", new BsonDouble(20.777));
        List<BsonDocument> list = new ArrayList<BsonDocument>();
        list.add(bsonObj);
        list.add(bsonObj);
        List<BsonArray> arrayList = new ArrayList<BsonArray>();
        arrayList.add(new BsonArray(list));
        arrayList.add(new BsonArray(list));
        byte[] bytes = new byte[3];
        bytes[0] = 3;
        bytes[1] = 2;
        bytes[2] = 1;
        BsonDocument bsonDocument = new BsonDocument().append("testDouble", new BsonDouble(20.99))
                .append("testString", new BsonString("testStringV"))
                .append("testArray", new BsonArray(list));
        return new BsonDocument().append("testDouble", new BsonDouble(20.99))
                .append("testString", new BsonString("testStringV"))
                .append("testArray", new BsonArray(list))
                .append("bson_test", bsonDocument)
                .append("testBinary", new BsonBinary(bytes))
                .append("testBsonUndefined", new BsonUndefined())
                .append("testObjectId", new BsonObjectId())
                .append("testStringObjectId", new BsonObjectId())
                .append("testBooean", new BsonBoolean(true))
                .append("testDate", new BsonDateTime(new Date().getTime()))
                .append("testNull", new BsonNull())
                .append("testInt", new BsonInt32(233))
                .append("testLong", new BsonInt64(233332));
    }

    @Test
    public void readFrom1() throws Exception {
        BsonDocument bsonDocument = getBsonDocument();
        BsonDocumentConverter bsonDocumentConverter = BsonValueConverterRepertory.getBsonDocumentConverter();
        long start = System.nanoTime();
        BsonDocumentReader bsonDocumentReader = new BsonDocumentReader(bsonDocument);
        BsonTest bsonTest = bsonDocumentConverter.decode(bsonDocumentReader, BsonTest.class);
        System.out.println(System.nanoTime() - start);
        long start2 = System.nanoTime();
        BsonTest bsonTest1 = bsonDocumentConverter.decode(bsonDocument, BsonTest.class);
        System.out.println(System.nanoTime() - start2);
        System.out.println(bsonTest);
        System.out.println(bsonTest1);
    }

    @Test
    public void readFrom2() throws Exception {

    }

    @Test
    public void readFrom3() throws Exception {

    }

}