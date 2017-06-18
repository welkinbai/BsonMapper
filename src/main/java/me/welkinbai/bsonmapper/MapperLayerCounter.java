package me.welkinbai.bsonmapper;

import me.welkinbai.bsonmapper.exception.BsonMapperConverterException;

/**
 * Created by welkinbai on 2017/6/17.
 */
public enum MapperLayerCounter {
    MAPPER_LAYER_COUNTER;

    private final ThreadLocal<Integer> threadLocalCount = new ThreadLocal<Integer>();

    public void addCount(BsonMapperConfig bsonMapperConfig) {
        Integer count = threadLocalCount.get();
        if (count == null) {
            threadLocalCount.set(0);
            return;
        }
        count++;
        if (count > bsonMapperConfig.getMaxMapperLayerNum()) {
            threadLocalCount.remove();
            throw new BsonMapperConverterException("exceed max layer in bsonMapperConfig.max layer is " + bsonMapperConfig.getMaxMapperLayerNum());
        }
        threadLocalCount.set(count);
    }

    public void reduceCount() {
        Integer count = threadLocalCount.get();
        if (count == null) {
            throw new BsonMapperConverterException("reduce count from map error.it should be never happen.please submit issue if happens");
        }
        count--;
        if (count < 0) {
            threadLocalCount.remove();
            return;
        }
        threadLocalCount.set(count);
    }


}
