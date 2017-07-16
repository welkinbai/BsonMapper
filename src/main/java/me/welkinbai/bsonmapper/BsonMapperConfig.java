package me.welkinbai.bsonmapper;

import org.bson.json.JsonWriterSettings;

/**
 * Created by welkinbai on 2017/6/17.
 */
public final class BsonMapperConfig {
    public static final BsonMapperConfig DEFALUT = BsonMapperConfigBuilder.Builder().setMaxMapperLayerNum(5).setMaxDocumentSizeForBsonWriter(2147483647).build();
    private final int maxMapperLayerNum;
    private final int maxDocumentSizeForBsonWriter;
    private final JsonWriterSettings jsonWriterSettings;

    private BsonMapperConfig(BsonMapperConfigBuilder bsonMapperConfigBuilder) {
        this.maxMapperLayerNum = bsonMapperConfigBuilder.maxMapperLayerNum;
        if (this.maxMapperLayerNum <= 0) {
            throw new IllegalArgumentException("maxMapperLayerNum should not less than or equal to 0.");
        }
        this.maxDocumentSizeForBsonWriter = bsonMapperConfigBuilder.maxDocumentSizeForBsonWriter;
        if (this.maxDocumentSizeForBsonWriter <= 0) {
            throw new IllegalArgumentException("maxDocumentSizeForBsonWriter should not less than or equal to 0.");
        }
        this.jsonWriterSettings = bsonMapperConfigBuilder.jsonWriterSettings;
    }

    public int getMaxMapperLayerNum() {
        return maxMapperLayerNum;
    }

    public int getMaxDocumentSizeForBsonWriter() {
        return maxDocumentSizeForBsonWriter;
    }

    public JsonWriterSettings getJsonWriterSettings() {
        return jsonWriterSettings;
    }

    public final static class BsonMapperConfigBuilder {
        private int maxMapperLayerNum;
        private int maxDocumentSizeForBsonWriter;
        private JsonWriterSettings jsonWriterSettings;

        private BsonMapperConfigBuilder() {
        }

        public static BsonMapperConfigBuilder Builder() {
            return new BsonMapperConfigBuilder();
        }

        public int getMaxMapperLayerNum() {
            return maxMapperLayerNum;
        }

        public BsonMapperConfigBuilder setMaxMapperLayerNum(int maxMapperLayerNum) {
            this.maxMapperLayerNum = maxMapperLayerNum;
            return this;
        }

        public int getMaxDocumentSizeForBsonWriter() {
            return maxDocumentSizeForBsonWriter;
        }

        public BsonMapperConfigBuilder setMaxDocumentSizeForBsonWriter(int maxDocumentSizeForBsonWriter) {
            this.maxDocumentSizeForBsonWriter = maxDocumentSizeForBsonWriter;
            return this;
        }

        public BsonMapperConfigBuilder setJsonWriterSettings(JsonWriterSettings jsonWriterSettings) {
            this.jsonWriterSettings = jsonWriterSettings;
            return this;
        }

        public JsonWriterSettings getJsonWriterSettings() {
            return jsonWriterSettings;
        }

        public BsonMapperConfig build() {
            return new BsonMapperConfig(this);
        }

    }
}
