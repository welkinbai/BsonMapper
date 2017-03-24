package me.welkinbai.bsonmapper.TestPOJO;

import me.welkinbai.bsonmapper.annotations.BsonArrayField;
import me.welkinbai.bsonmapper.annotations.BsonField;

import java.util.List;

/**
 * Created by welkinbai on 2017/3/23.
 */
public class BsonTest {
    private Double testDouble;
    private String testString;
    @BsonArrayField(componentType = BsonTest.class)
    private List<BsonTest> testArray;
    @BsonField("bson_test")
    private BsonTest bsonTest;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BsonTest{");
        sb.append("testDouble=").append(testDouble);
        sb.append(", testString='").append(testString).append('\'');
        sb.append(", testArray=").append(testArray);
        sb.append(", bsonTest=").append(bsonTest);
        sb.append('}');
        return sb.toString();
    }
}
