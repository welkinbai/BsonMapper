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
    private double testDouble;
    private String testString;
    private Binary testBinary;
    @BsonArrayField(componentType = BsonTest.class)
    private List<BsonTest> testArray;
    @BsonField(value = "bson_test")
    private BsonTest bsonTest;
    private ObjectId testObjectId;
    @BsonField(IsObjectId = true)
    private String testStringObjectId;
    //    @BsonIgnore
    private boolean testBoolean;
    private Date testDate;
    private BsonTest testNull;
    private int testInt;
    private long testLong;

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

    public boolean isTestBoolean() {
        return testBoolean;
    }

    public Date getTestDate() {
        return testDate;
    }

    public BsonTest getTestNull() {
        return testNull;
    }

    public Integer getTestInt() {
        return testInt;
    }

    public Long getTestLong() {
        return testLong;
    }

    public void setTestDouble(Double testDouble) {
        this.testDouble = testDouble;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

    public void setTestBinary(Binary testBinary) {
        this.testBinary = testBinary;
    }

    public void setTestArray(List<BsonTest> testArray) {
        this.testArray = testArray;
    }

    public void setBsonTest(BsonTest bsonTest) {
        this.bsonTest = bsonTest;
    }

    public void setTestObjectId(ObjectId testObjectId) {
        this.testObjectId = testObjectId;
    }

    public void setTestStringObjectId(String testStringObjectId) {
        this.testStringObjectId = testStringObjectId;
    }

    public void setTestBoolean(boolean testBoolean) {
        this.testBoolean = testBoolean;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public void setTestNull(BsonTest testNull) {
        this.testNull = testNull;
    }

    public void setTestInt(Integer testInt) {
        this.testInt = testInt;
    }

    public void setTestLong(Long testLong) {
        this.testLong = testLong;
    }

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
        sb.append(", testBoolean=").append(testBoolean);
        sb.append(", testDate=").append(testDate);
        sb.append(", testNull=").append(testNull);
        sb.append(", testInt=").append(testInt);
        sb.append(", testLong=").append(testLong);
        sb.append('}');
        return sb.toString();
    }

}
