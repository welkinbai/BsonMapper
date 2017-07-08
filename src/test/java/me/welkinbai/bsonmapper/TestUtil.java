package me.welkinbai.bsonmapper;

import me.welkinbai.bsonmapper.TestPOJO.BsonTest;
import org.bson.BsonArray;
import org.bson.BsonBinary;
import org.bson.BsonBoolean;
import org.bson.BsonDateTime;
import org.bson.BsonDocument;
import org.bson.BsonDouble;
import org.bson.BsonInt32;
import org.bson.BsonInt64;
import org.bson.BsonNull;
import org.bson.BsonObjectId;
import org.bson.BsonString;
import org.bson.BsonUndefined;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by welkinbai on 2017/7/8.
 */
public class TestUtil {


    private static long time = new Date().getTime();

    public static BsonDocument getBsonDocument() {
        BsonDocument bsonObj = new BsonDocument().append("testDouble", new BsonDouble(20.777));
        List<BsonDocument> list = new ArrayList<BsonDocument>();
        list.add(bsonObj);
        list.add(bsonObj);
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
                .append("testBoolean", new BsonBoolean(true))
                .append("testDate", new BsonDateTime(time))
                .append("testNull", new BsonNull())
                .append("testInt", new BsonInt32(233))
                .append("testLong", new BsonInt64(233332));
    }


    public static void checkBsonTest(BsonTest bsonTest) {
        Assert.assertEquals(20.99, bsonTest.getTestDouble(), 0);
        Assert.assertEquals("testStringV", bsonTest.getTestString());
        Assert.assertNotNull(bsonTest.getTestArray());
        for (BsonTest innerTestObj : bsonTest.getTestArray()) {
            Assert.assertEquals(20.777, innerTestObj.getTestDouble(), 0);
            Assert.assertNull(innerTestObj.getTestString());
        }
        Assert.assertNotNull(bsonTest.getBsonTest());
        BsonTest testHasTest = bsonTest.getBsonTest();
        BsonTest expected = new BsonTest();
        expected.setTestDouble(20.99);
        expected.setTestString("testStringV");
        ArrayList<BsonTest> testArray = new ArrayList<BsonTest>();
        BsonTest arrayInner = new BsonTest();
        arrayInner.setTestDouble(20.777);
        testArray.add(arrayInner);
        testArray.add(arrayInner);
        expected.setTestArray(testArray);
        Assert.assertEquals(expected, testHasTest);
        byte[] bytes = new byte[3];
        bytes[0] = 3;
        bytes[1] = 2;
        bytes[2] = 1;
        Assert.assertEquals(new Binary(bytes), bsonTest.getTestBinary());
        Assert.assertNotNull(bsonTest.getTestObjectId());
        Assert.assertTrue(bsonTest.getTestObjectId().getClass() == ObjectId.class);
        Assert.assertNotNull(bsonTest.getTestStringObjectId());
        Assert.assertNull(bsonTest.getTestIgnore());
        Assert.assertTrue(bsonTest.isTestBoolean());
        Assert.assertEquals(new Date(time), bsonTest.getTestDate());
        Assert.assertEquals(null, bsonTest.getTestNull());
        Assert.assertTrue(bsonTest.getTestInt() == 233);
        Assert.assertTrue(bsonTest.getTestLong() == 233332L);
    }
}
