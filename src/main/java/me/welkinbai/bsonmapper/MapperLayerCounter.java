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
            threadLocalCount.set(1);
            return;
        }
        count++;
        if (count > bsonMapperConfig.getMaxMapperLayerNum()) {
            threadLocalCount.remove();
            throw new BsonMapperConverterException(String.format("exceed max layer in bsonMapperConfig.current layer is %s.max layer is %s", count, bsonMapperConfig.getMaxMapperLayerNum()));
        }
        threadLocalCount.set(count);
    }

    public void reduceCount() {
        Integer count = threadLocalCount.get();
        if (count == null) {
            return;
        }
        count--;
        if (count < 0) {
            threadLocalCount.remove();
            return;
        }
        threadLocalCount.set(count);
    }


}
