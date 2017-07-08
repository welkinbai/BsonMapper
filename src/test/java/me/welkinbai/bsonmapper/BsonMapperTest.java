package me.welkinbai.bsonmapper;

import me.welkinbai.bsonmapper.TestPOJO.BsonTest;
import org.bson.BsonBinaryWriter;
import org.bson.BsonDocument;
import org.bson.BsonWriter;
import org.bson.ByteBufNIO;
import org.bson.io.BasicOutputBuffer;
import org.bson.io.BsonInput;
import org.bson.io.ByteBufferBsonInput;
import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;

import static me.welkinbai.bsonmapper.TestUtil.getBsonDocument;

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
    public void testReadFromBsonDocument() throws Exception{
        BsonDocument bsonDocument = getBsonDocument();
        BsonTest bsonTest = bsonMapper.readFrom(bsonDocument, BsonTest.class);
        TestUtil.checkBsonTest(bsonTest);
    }

    @Test
    public void testReadFromInput() throws Exception{
        BsonDocument bsonDocument = getBsonDocument();
        BasicOutputBuffer bsonOutput = new BasicOutputBuffer();
        BsonWriter bsonWriter = new BsonBinaryWriter(bsonOutput);
        ByteBufNIO buffer = new ByteBufNIO(ByteBuffer.wrap(new byte[2048]));
        BsonInput bsonInput = new ByteBufferBsonInput(buffer);
        BsonTest bsonTest = bsonMapper.readFrom(bsonInput, BsonTest.class);
        System.out.println(bsonTest);
    }

}
