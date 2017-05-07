package me.welkinbai.bsonmapper;

import me.welkinbai.bsonmapper.TestPOJO.BsonTest;
import org.bson.BsonArray;
import org.bson.BsonBinary;
import org.bson.BsonBinaryReader;
import org.bson.BsonBinarySubType;
import org.bson.BsonBinaryWriter;
import org.bson.BsonBoolean;
import org.bson.BsonDateTime;
import org.bson.BsonDocument;
import org.bson.BsonDouble;
import org.bson.BsonInt32;
import org.bson.BsonInt64;
import org.bson.BsonNull;
import org.bson.BsonObjectId;
import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonUndefined;
import org.bson.BsonWriter;
import org.bson.io.BasicOutputBuffer;
import org.bson.io.ByteBufferBsonInput;
import org.bson.json.JsonWriter;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.junit.Test;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by welkinbai on 2017/3/23.
 */
public class BsonDocumentConverterTest {

    @Test
    public void decode() throws Exception {
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
        BsonDocument bsonDocument1 = new BsonDocument().append("testDouble", new BsonDouble(20.99))
                .append("testString", new BsonString("testStringV"))
                .append("testArray", new BsonArray(list))
                .append("bson_test", bsonDocument)
                .append("testBinary", new BsonBinary(bytes))
                .append("testBsonUndefined", new BsonUndefined())
                .append("testObjectId", new BsonObjectId())
                .append("testStringObjectId", new BsonObjectId())
                .append("testBoolean", new BsonBoolean(true))
                .append("testDate", new BsonDateTime(new Date().getTime()))
                .append("testNull", new BsonNull())
                .append("testInt", new BsonInt32(233))
                .append("testLong", new BsonInt64(233332));
        BsonTest bsonTest = BsonDocumentConverter.getInstance().decode(bsonDocument1, BsonTest.class);
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

    @Test
    public void testEncode() throws Exception {
        BsonDocument bsonDocument = new BsonDocument();
        BsonTest object = getObject();
        BsonValueConverterRepertory.getBsonDocumentConverter().encode(bsonDocument, object);
        System.out.println(bsonDocument.toJson());
        BsonTest bsonTest = BsonValueConverterRepertory.getBsonDocumentConverter().decode(bsonDocument, BsonTest.class);
        System.out.println(bsonTest);
    }

    @Test
    public void testByteIOEncode() throws Exception {
        BasicOutputBuffer bsonOutput = new BasicOutputBuffer();
        BsonWriter bsonWriter = new BsonBinaryWriter(bsonOutput);
        BsonValueConverterRepertory.getBsonDocumentConverter().encode(bsonWriter, getObject());
        BsonReader bsonReader = new BsonBinaryReader(new ByteBufferBsonInput(bsonOutput.getByteBuffers().get(0)));
        BsonTest bsonTest = BsonValueConverterRepertory.getBsonDocumentConverter().decode(bsonReader, BsonTest.class);
        System.out.println(bsonTest);
    }

    @Test
    public void testJsonEncode() throws Exception {
        StringWriter writer = new StringWriter();
        BsonWriter bsonWriter = new JsonWriter(writer);
        BsonValueConverterRepertory.getBsonDocumentConverter().encode(bsonWriter, getObject());
        System.out.println(writer.toString());
    }

    private BsonTest getObject() {
        BsonTest bsonTest = new BsonTest();
        bsonTest.setTestDouble(20.00);
        bsonTest.setTestString("测试一下");
        byte[] bytes = new byte[3];
        bytes[0] = 3;
        bytes[1] = 4;
        bytes[2] = 1;
        bsonTest.setTestBinary(new Binary(BsonBinarySubType.USER_DEFINED, bytes));
        ArrayList<BsonTest> testArray = new ArrayList<BsonTest>();
        BsonTest test1 = new BsonTest();
        test1.setTestDouble(30.00);
        testArray.add(test1);
        bsonTest.setTestArray(testArray);
        bsonTest.setBsonTest(test1);
        ObjectId testObjectId = new ObjectId();
        bsonTest.setTestObjectId(testObjectId);
        bsonTest.setTestStringObjectId(testObjectId.toHexString());
        bsonTest.setTestBoolean(true);
        bsonTest.setTestDate(new Date());
        bsonTest.setTestNull(null);
        bsonTest.setTestInt(2333);
        bsonTest.setTestLong(2222L);
        return bsonTest;
    }

    @Test
    public void testObjectId() throws Exception {
        ObjectId objectId = ObjectId.get();
        System.out.println(objectId);
        System.out.println(objectId.toHexString());
    }

}