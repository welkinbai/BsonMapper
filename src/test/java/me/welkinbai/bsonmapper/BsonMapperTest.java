package me.welkinbai.bsonmapper;

import me.welkinbai.bsonmapper.TestPOJO.BsonTest;
import org.bson.BsonDocument;
import org.bson.ByteBufNIO;
import org.bson.io.BasicOutputBuffer;
import org.bson.io.ByteBufferBsonInput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;

import static me.welkinbai.bsonmapper.TestUtil.getBsonByte;
import static me.welkinbai.bsonmapper.TestUtil.getBsonDocument;
import static me.welkinbai.bsonmapper.TestUtil.getBsonObject;

/**
 * Created by welkinbai on 2017/7/8.
 */
public class BsonMapperTest {


    private BsonMapper bsonMapper;

    @Before
    public void setUp() throws Exception {
        bsonMapper = DefaultBsonMapper.defaultBsonMapper();
    }

    @Test
    public void testReadFromBsonDocument() throws Exception {
        BsonDocument bsonDocument = getBsonDocument();
        BsonTest bsonTest = bsonMapper.readFrom(bsonDocument, BsonTest.class);
        TestUtil.checkBsonTest(bsonTest);
    }

    @Test
    public void testReadFromInput() throws Exception {
        byte[] bsonObject = TestUtil.getBsonByte();
        ByteBufferBsonInput bsonInput = new ByteBufferBsonInput(new ByteBufNIO(ByteBuffer.wrap(bsonObject)));
        BsonTest bsonTest = bsonMapper.readFrom(bsonInput, BsonTest.class);
        Assert.assertTrue("boolean should be true.", bsonTest.isTestBoolean());
        Assert.assertEquals("double should be 20.99", 20.99, bsonTest.getTestDouble(), 0);
        Assert.assertEquals("string should be testStringV", "testStringV", bsonTest.getTestString());
        Assert.assertEquals("Long should be 123", 123L, bsonTest.getTestLong(), 0);
    }

    @Test
    public void testWriteToOutput() throws Exception {
        BsonTest bsonObject = getBsonObject();
        BasicOutputBuffer bsonOutput = (BasicOutputBuffer) bsonMapper.writeToBsonOutput(bsonObject);
        byte[] internalBuffer = bsonOutput.getInternalBuffer();
        byte[] expectedBytes = getBsonByte();
        Assert.assertArrayEquals("bytes should be equals", expectedBytes, internalBuffer);
    }

}
