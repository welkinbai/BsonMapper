package me.welkinbai.bsonmapper.TestPOJO;

import me.welkinbai.bsonmapper.annotations.BsonArrayField;
import me.welkinbai.bsonmapper.annotations.BsonField;
import org.bson.types.Binary;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

/**
 * Created by welkinbai on 2017/3/23.
 */
public class BsonTest {
    private Double testDouble;
    private String testString;
    private Binary testBinary;
    //    private BsonUndefined testBsonUndefined;
    @BsonArrayField(componentType = BsonTest.class)
    private List<BsonTest> testArray;
    @BsonField(value = "bson_test")
    private BsonTest bsonTest;
    private ObjectId testObjectId;
    @BsonField(IsObjectId = true)
    private String testStringObjectId;
    private boolean testBooean;
    private Date testDate;
    private BsonTest testNull;

    public Double getTestDouble() {
        return testDouble;
    }

    public String getTestString() {
        return testString;
    }

    public List<BsonTest> getTestArray() {
        return testArray;
    }

    public BsonTest getBsonTest() {
        return bsonTest;
    }

    public Binary getTestBinary() {
        return testBinary;
    }

    public ObjectId getTestObjectId() {
        return testObjectId;
    }

    public String getTestStringObjectId() {
        return testStringObjectId;
    }

    public boolean isTestBooean() {
        return testBooean;
    }

    public Date getTestDate() {
        return testDate;
    }

    public BsonTest getTestNull() {
        return testNull;
    }

    //    public BsonUndefined getTestBsonUndefined() {
//        return testBsonUndefined;
//    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BsonTest{");
        sb.append("testDouble=").append(testDouble);
        sb.append(", testString='").append(testString).append('\'');
        sb.append(", testBinary=").append(testBinary);
        sb.append(", testArray=").append(testArray);
        sb.append(", bsonTest=").append(bsonTest);
        sb.append(", testObjectId=").append(testObjectId);
        sb.append(", testStringObjectId='").append(testStringObjectId).append('\'');
        sb.append(", testBooean=").append(testBooean);
        sb.append(", testDate=").append(testDate);
        sb.append('}');
        return sb.toString();
    }
}
