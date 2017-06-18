package me.welkinbai.bsonmapper;

/**
 * Created by welkinbai on 2017/6/17.
 */
public final class BsonMapperConfig {
    public static final BsonMapperConfig DEFALUT = BsonMapperConfigBuilder.Builder().setMaxMapperLayerNum(5).build();
    private final int maxMapperLayerNum;

    private BsonMapperConfig(BsonMapperConfigBuilder bsonMapperConfigBuilder) {
        this.maxMapperLayerNum = bsonMapperConfigBuilder.maxMapperLayerNum;
        if (this.maxMapperLayerNum <= 0) {
            throw new IllegalArgumentException("maxMapperLayerNum should not less than or equal to 0.");
        }
    }

    public int getMaxMapperLayerNum() {
        return maxMapperLayerNum;
    }

    public final static class BsonMapperConfigBuilder {
        private int maxMapperLayerNum;

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

        public BsonMapperConfig build() {
            return new BsonMapperConfig(this);
        }

    }
}
