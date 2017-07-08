package me.welkinbai.bsonmapper.TestPOJO;

import me.welkinbai.bsonmapper.annotations.BsonArrayField;
import me.welkinbai.bsonmapper.annotations.BsonField;
import me.welkinbai.bsonmapper.annotations.BsonIgnore;
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
    @BsonIgnore
    private Boolean testIgnore;
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

    public void setTestDouble(double testDouble) {
        this.testDouble = testDouble;
    }

    public Boolean getTestIgnore() {
        return testIgnore;
    }

    public void setTestIgnore(Boolean testIgnore) {
        this.testIgnore = testIgnore;
    }

    public void setTestInt(int testInt) {
        this.testInt = testInt;
    }

    public void setTestLong(long testLong) {
        this.testLong = testLong;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        BsonTest bsonTest1 = (BsonTest) object;

        if (Double.compare(bsonTest1.testDouble, testDouble) != 0) return false;
        if (testIgnore != bsonTest1.testIgnore) return false;
        if (testBoolean != bsonTest1.testBoolean) return false;
        if (testInt != bsonTest1.testInt) return false;
        if (testLong != bsonTest1.testLong) return false;
        if (testString != null ? !testString.equals(bsonTest1.testString) : bsonTest1.testString != null) return false;
        if (testBinary != null ? !testBinary.equals(bsonTest1.testBinary) : bsonTest1.testBinary != null) return false;
        if (testArray != null ? !testArray.equals(bsonTest1.testArray) : bsonTest1.testArray != null) return false;
        if (bsonTest != null ? !bsonTest.equals(bsonTest1.bsonTest) : bsonTest1.bsonTest != null) return false;
        if (testObjectId != null ? !testObjectId.equals(bsonTest1.testObjectId) : bsonTest1.testObjectId != null) return false;
        if (testStringObjectId != null ? !testStringObjectId.equals(bsonTest1.testStringObjectId) : bsonTest1.testStringObjectId != null)
            return false;
        if (testDate != null ? !testDate.equals(bsonTest1.testDate) : bsonTest1.testDate != null) return false;
        return testNull != null ? testNull.equals(bsonTest1.testNull) : bsonTest1.testNull == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(testDouble);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (testString != null ? testString.hashCode() : 0);
        result = 31 * result + (testBinary != null ? testBinary.hashCode() : 0);
        result = 31 * result + (testArray != null ? testArray.hashCode() : 0);
        result = 31 * result + (bsonTest != null ? bsonTest.hashCode() : 0);
        result = 31 * result + (testObjectId != null ? testObjectId.hashCode() : 0);
        result = 31 * result + (testStringObjectId != null ? testStringObjectId.hashCode() : 0);
        result = 31 * result + (testIgnore ? 1 : 0);
        result = 31 * result + (testBoolean ? 1 : 0);
        result = 31 * result + (testDate != null ? testDate.hashCode() : 0);
        result = 31 * result + (testNull != null ? testNull.hashCode() : 0);
        result = 31 * result + testInt;
        result = 31 * result + (int) (testLong ^ (testLong >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "BsonTest{" +
                "testDouble=" + testDouble +
                ", testString='" + testString + '\'' +
                ", testBinary=" + testBinary +
                ", testArray=" + testArray +
                ", bsonTest=" + bsonTest +
                ", testObjectId=" + testObjectId +
                ", testStringObjectId='" + testStringObjectId + '\'' +
                ", testIgnore=" + testIgnore +
                ", testBoolean=" + testBoolean +
                ", testDate=" + testDate +
                ", testNull=" + testNull +
                ", testInt=" + testInt +
                ", testLong=" + testLong +
                '}';
    }
}
