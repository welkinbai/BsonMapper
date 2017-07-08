package me.welkinbai.bsonmapper;

import me.welkinbai.bsonmapper.BsonMapperConfig.BsonMapperConfigBuilder;
import me.welkinbai.bsonmapper.TestPOJO.BsonTest;
import org.bson.BsonArray;
import org.bson.BsonBinarySubType;
import org.bson.BsonDocument;
import org.bson.BsonDocumentReader;
import org.bson.BsonDouble;
import org.bson.BsonInt64;
import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static me.welkinbai.bsonmapper.TestUtil.getBsonDocument;

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

    @Test
    public void testReadFromWithConfig() throws Exception {
        final BsonDocument bsonDocumentHasDeepLayer = getBsonDocumentHasDeepLayer();
        System.out.println(bsonDocumentHasDeepLayer.toJson());
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < 2; i++) {
            final int finalI = i;
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    BsonMapper bsonMapper = DefaultBsonMapper.defaultBsonMapper(BsonMapperConfigBuilder.Builder().setMaxMapperLayerNum(9 - finalI).build());
                    BsonTest bsonTest = bsonMapper.readFrom(bsonDocumentHasDeepLayer, BsonTest.class);
                    System.out.println(bsonTest);
                }
            }));
        }
        for (Thread thread : threads) {
            thread.run();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }

    private BsonDocument getBsonDocumentHasDeepLayer() {
        BsonDocument bsonObj = new BsonDocument().append("testDouble", new BsonDouble(20.777));
        List<BsonDocument> list = new ArrayList<BsonDocument>();
        list.add(bsonObj);
        list.add(bsonObj);
        BsonDocument deepBsonDocument = new BsonDocument().append("testDouble", new BsonDouble(20.99))
                .append("testString", new BsonString("testStringV"))
                .append("testArray", new BsonArray(list));
        BsonDocument bsonDocument1 = new BsonDocument().append("testDouble", new BsonDouble(20.99))
                .append("testString", new BsonString("testStringV"))
                .append("bson_test", deepBsonDocument);
        BsonDocument bsonDocument2 = new BsonDocument().append("testDouble", new BsonDouble(20.99))
                .append("testString", new BsonString("testStringV"))
                .append("bson_test", bsonDocument1);
        BsonDocument bsonDocument3 = new BsonDocument().append("testDouble", new BsonDouble(20.99))
                .append("testString", new BsonString("testStringV"))
                .append("bson_test", bsonDocument2)
                .append("testArray", new BsonArray(list));
        BsonDocument bsonDocument4 = new BsonDocument().append("testDouble", new BsonDouble(20.99))
                .append("testString", new BsonString("testStringV"))
                .append("bson_test", bsonDocument3)
                .append("testArray", new BsonArray(list));
        BsonDocument bsonDocument5 = new BsonDocument().append("testDouble", new BsonDouble(20.99))
                .append("testString", new BsonString("testStringV"))
                .append("bson_test", bsonDocument4)
                .append("testArray", new BsonArray(list));
        return new BsonDocument().append("testDouble", new BsonDouble(20.99))
                .append("bson_test", bsonDocument5)
                .append("testLong", new BsonInt64(233332));
    }

    @Test
    public void testCustomizedBsonConverter() throws Exception {
        BsonValueConverterRepertory.registerCustomizedBsonConverter(String.class, new AbstractBsonConverter<String, BsonString>() {
            @Override
            public String decode(BsonReader bsonReader) {
                return "replace string";
            }

            @Override
            public void encode(BsonWriter bsonWriter, String value) {

            }

            @Override
            public String decode(BsonValue bsonValue) {
                return "replace string";
            }

            @Override
            public BsonString encode(String object) {
                return new BsonString("replace string");
            }
        });
        readFrom();
    }

    @Test
    public void testWriteTo() throws Exception {
        BsonMapper bsonMapper = DefaultBsonMapper.defaultBsonMapper();
        BsonDocument bsonDocument = bsonMapper.writeToBsonDocument(getObject());
        System.out.println(bsonDocument.toJson());

    }

    @Test
    public void testIsGatter() throws Exception {
        BsonTest bsonTest = new BsonTest();
        bsonTest.setTestBoolean(true);
        Object value = Utils.getFieldValue(BsonTest.class.getDeclaredField("testBoolean"), bsonTest);
        System.out.println(value);
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
        bsonTest.setTestObjectId(new ObjectId());
        bsonTest.setTestStringObjectId("59074307568fad36808ff0c5");
        bsonTest.setTestBoolean(true);
        bsonTest.setTestDate(new Date());
        bsonTest.setTestNull(null);
        bsonTest.setTestInt(2333);
        bsonTest.setTestLong(2222L);
        return bsonTest;
    }

    @Test
    public void readFrom1() throws Exception {
        BsonDocument bsonDocument = getBsonDocument();
        BsonDocumentConverter bsonDocumentConverter = BsonValueConverterRepertory.getBsonDocumentConverter();
        long start = System.nanoTime();
        BsonDocumentReader bsonDocumentReader = new BsonDocumentReader(bsonDocument);
        BsonTest bsonTest = bsonDocumentConverter.decode(bsonDocumentReader, BsonTest.class, BsonMapperConfig.DEFALUT);
        System.out.println(System.nanoTime() - start);
        long start2 = System.nanoTime();
        BsonTest bsonTest1 = bsonDocumentConverter.decode(bsonDocument, BsonTest.class, BsonMapperConfig.DEFALUT);
        System.out.println(System.nanoTime() - start2);
        System.out.println(bsonTest);
        System.out.println(bsonTest1);
    }


}