package me.welkinbai.bsonmapper;

import org.bson.BsonBoolean;
import org.bson.BsonDateTime;
import org.bson.BsonDocument;
import org.bson.BsonDouble;
import org.bson.BsonInt32;
import org.bson.BsonInt64;
import org.bson.BsonObjectId;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.codecs.Encoder;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by welkinbai on 2017/5/21.
 */
public class TDocument<T> extends Document {
    private final BsonDocument innerBsonDocument;
    private T equivalentPOJO;
    private Class<?> equivalentPOJOClazz;

    private TDocument(BsonDocument bsonDocument, Class<?> InnerPOJOClazz) {
        this.innerBsonDocument = bsonDocument;
        this.equivalentPOJOClazz = InnerPOJOClazz;
    }

    public static <T> TDocument<T> fromBsonDocument(BsonDocument bsonDocument, Class<?> InnerPOJOClazz) {
        return new TDocument<T>(bsonDocument, InnerPOJOClazz);
    }

    @Override
    public <C> BsonDocument toBsonDocument(Class<C> documentClass, CodecRegistry codecRegistry) {
        return super.toBsonDocument(documentClass, codecRegistry);
    }

    public BsonDocument toBsonDocument() {
        return this.innerBsonDocument;
    }

    @Override
    public Document append(String key, Object value) {
        innerBsonDocument.append(key, BsonValueConverterRepertory.getValueConverterByClazz(value.getClass()).encode(value));
        return this;
    }

    @Override
    public <P> P get(Object key, Class<P> clazz) {
        BsonValue bsonValue = innerBsonDocument.get(key);
        if (bsonValue != null && BsonValueConverterRepertory.isCanConverterValueType(clazz)) {
            return (P) BsonValueConverterRepertory.getValueConverterByClazz(clazz).decode(bsonValue);
        } else {
            return null;
        }
    }

    @Override
    public Integer getInteger(Object key) {
        return getInt32FromInner(key);
    }

    @Override
    public int getInteger(Object key, int defaultValue) {
        Integer int32 = getInt32FromInner(key);
        return int32 == null ? defaultValue : int32;
    }

    private Integer getInt32FromInner(Object key) {
        BsonInt32 int32 = innerBsonDocument.getInt32(key);
        if (int32 != null) {
            return (Integer) BsonValueConverterRepertory.getValueConverterByBsonType(int32.getBsonType()).decode(int32);
        } else {
            return null;
        }
    }

    @Override
    public Long getLong(Object key) {
        BsonInt64 int64 = innerBsonDocument.getInt64(key);
        if (int64 != null) {
            return (Long) BsonValueConverterRepertory.getValueConverterByBsonType(int64.getBsonType()).decode(int64);
        } else {
            return null;
        }
    }

    @Override
    public Double getDouble(Object key) {
        BsonDouble aDouble = innerBsonDocument.getDouble(key);
        if (aDouble != null) {
            return (Double) BsonValueConverterRepertory.getValueConverterByBsonType(aDouble.getBsonType()).decode(aDouble);
        } else {
            return null;
        }
    }

    @Override
    public String getString(Object key) {
        BsonString bsonString = innerBsonDocument.getString(key);
        if (bsonString != null) {
            return (String) BsonValueConverterRepertory.getValueConverterByBsonType(bsonString.getBsonType()).decode(bsonString);
        } else {
            return null;
        }
    }

    @Override
    public Boolean getBoolean(Object key) {
        return getBooleanFromInner(key);
    }

    private Boolean getBooleanFromInner(Object key) {
        BsonBoolean bsonBoolean = innerBsonDocument.getBoolean(key);
        if (bsonBoolean != null) {
            return (Boolean) BsonValueConverterRepertory.getValueConverterByBsonType(bsonBoolean.getBsonType()).decode(bsonBoolean);
        }
        return null;
    }

    @Override
    public boolean getBoolean(Object key, boolean defaultValue) {
        Boolean booleanFromInner = getBooleanFromInner(key);
        return booleanFromInner == null ? defaultValue : booleanFromInner;
    }

    @Override
    public ObjectId getObjectId(Object key) {
        BsonObjectId objectId = innerBsonDocument.getObjectId(key);
        if (objectId != null) {
            return (ObjectId) BsonValueConverterRepertory.getValueConverterByBsonType(objectId.getBsonType()).decode(objectId);
        } else {
            return null;
        }
    }

    @Override
    public Date getDate(Object key) {
        BsonDateTime dateTime = innerBsonDocument.getDateTime(key);
        if (dateTime != null) {
            return (Date) BsonValueConverterRepertory.getValueConverterByBsonType(dateTime.getBsonType()).decode(dateTime);
        } else {
            return null;
        }
    }

    @Override
    public String toJson() {
        return innerBsonDocument.toJson();
    }

    @Override
    public String toJson(JsonWriterSettings writerSettings) {
        return innerBsonDocument.toJson(writerSettings);
    }

    @Override
    public String toJson(Encoder<Document> encoder) {
        throw new UnsupportedOperationException("inner bsonDocument not support toJson(encoder).");
    }

    @Override
    public String toJson(JsonWriterSettings writerSettings, Encoder<Document> encoder) {
        throw new UnsupportedOperationException("inner bsonDocument not support toJson(encoder).");
    }

    @Override
    public int size() {
        return innerBsonDocument.size();
    }

    @Override
    public boolean isEmpty() {
        return innerBsonDocument.isEmpty();
    }

    @Override
    public boolean containsValue(Object value) {
        Utils.checkNotNull(value, "value should not be null");
        return innerBsonDocument.containsValue(BsonValueConverterRepertory.getValueConverterByClazz(value.getClass()).encode(value));
    }

    @Override
    public boolean containsKey(Object key) {
        return innerBsonDocument.containsKey(key);
    }

    @Override
    public Object get(Object key) {
        BsonValue bsonValue = innerBsonDocument.get(key);
        if (bsonValue == null) {
            return null;
        }
        return BsonValueConverterRepertory.getValueConverterByClazz(bsonValue.getClass()).decode(bsonValue);
    }

    @Override
    public Object put(String key, Object value) {
        Utils.checkNotNull(value, String.format("The value for key %s can not be null", key));
        return innerBsonDocument.put(key, BsonValueConverterRepertory.getValueConverterByClazz(value.getClass()).encode(value));
    }

    @Override
    public Object remove(Object key) {
        return innerBsonDocument.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> map) {
        for (Entry<? extends String, ?> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        innerBsonDocument.clear();
    }

    @Override
    public Set<String> keySet() {
        return innerBsonDocument.keySet();
    }

    @Override
    public Collection<Object> values() {
        Collection<Object> result = new ArrayList<Object>(innerBsonDocument.size());
        for (BsonValue bsonValue : innerBsonDocument.values()) {
            result.add(BsonValueConverterRepertory.getValueConverterByBsonType(bsonValue.getBsonType()).decode(bsonValue));
        }
        return result;
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        HashMap<String, Object> resultMap = new HashMap<String, Object>(innerBsonDocument.size());
        for (Entry<String, BsonValue> entry : innerBsonDocument.entrySet()) {
            BsonValue value = entry.getValue();
            resultMap.put(entry.getKey(), BsonValueConverterRepertory.getValueConverterByBsonType(value.getBsonType()).encode(value));
        }
        return resultMap.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            TDocument document = (TDocument) o;
            return this.innerBsonDocument.equals(document.innerBsonDocument);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.innerBsonDocument.hashCode();
    }

    @Override
    public String toString() {
        return this.innerBsonDocument.toString();
    }

    public T getEquivalentPOJO(BsonMapper bsonMapper) {
        if (equivalentPOJO == null) {
            this.equivalentPOJO = (T) bsonMapper.readFrom(innerBsonDocument, equivalentPOJOClazz);
        }
        return equivalentPOJO;
    }

}
