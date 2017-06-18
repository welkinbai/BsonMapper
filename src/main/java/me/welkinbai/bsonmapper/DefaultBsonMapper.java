package me.welkinbai.bsonmapper;

import org.bson.BsonBinaryReader;
import org.bson.BsonBinaryWriter;
import org.bson.BsonBinaryWriterSettings;
import org.bson.BsonDocument;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.BsonWriterSettings;
import org.bson.Document;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.io.BasicOutputBuffer;
import org.bson.io.BsonInput;
import org.bson.io.BsonOutput;
import org.bson.json.JsonReader;
import org.bson.json.JsonWriter;
import org.bson.json.JsonWriterSettings;

import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.util.Map.Entry;

import static me.welkinbai.bsonmapper.Utils.checkIsSupportClazz;
import static me.welkinbai.bsonmapper.Utils.checkNotNull;
import static me.welkinbai.bsonmapper.Utils.isStrEmpty;

/**
 * Created by welkinbai on 2017/3/21.
 */
public class DefaultBsonMapper implements BsonMapper, MongoBsonMapper {

    private static final String NOT_NULL_MSG = "targetClazz should not be Null!";
    private static final String NOT_SUPPORT_CLAZZ_MSG = "targetClazz should not be a common value Clazz or Collection/Array Clazz.It should be a real Object.clazz name:";
    private final BsonMapperConfig bsonMapperConfig;

    private DefaultBsonMapper(BsonMapperConfig bsonMapperConfig) {
        this.bsonMapperConfig = bsonMapperConfig;
    }

    public static BsonMapper defaultBsonMapper() {
        return new DefaultBsonMapper(BsonMapperConfig.DEFALUT);
    }

    public static BsonMapper defaultBsonMapper(BsonMapperConfig userCustomizedConfig) {
        return new DefaultBsonMapper(userCustomizedConfig);
    }

    public static MongoBsonMapper defaultMongoBsonMapper() {
        return new DefaultBsonMapper(BsonMapperConfig.DEFALUT);
    }

    public static MongoBsonMapper defaultMongoBsonMapper(BsonMapperConfig userCustomizedConfig) {
        return new DefaultBsonMapper(userCustomizedConfig);
    }

    @Override
    public <T> T readFrom(BsonDocument bsonDocument, Class<T> targetClazz) {
        if (bsonDocument == null) {
            return null;
        }
        checkNotNull(targetClazz, NOT_NULL_MSG);
        checkIsSupportClazz(targetClazz, NOT_SUPPORT_CLAZZ_MSG + targetClazz.getName());
        return BsonValueConverterRepertory.getBsonDocumentConverter().decode(bsonDocument, targetClazz, bsonMapperConfig);
    }

    @Override
    public <T> T readFrom(ByteBuffer byteBuffer, Class<T> targetClazz) {
        if (byteBuffer == null) {
            return null;
        }
        checkNotNull(targetClazz, NOT_NULL_MSG);
        checkIsSupportClazz(targetClazz, NOT_SUPPORT_CLAZZ_MSG + targetClazz.getName());
        BsonBinaryReader bsonBinaryReader = new BsonBinaryReader(byteBuffer);
        return BsonValueConverterRepertory.getBsonDocumentConverter().decode(bsonBinaryReader, targetClazz, bsonMapperConfig);
    }

    @Override
    public <T> T readFrom(BsonInput bsonInput, Class<T> targetClazz) {
        if (bsonInput == null) {
            return null;
        }
        checkNotNull(targetClazz, NOT_NULL_MSG);
        checkIsSupportClazz(targetClazz, NOT_SUPPORT_CLAZZ_MSG + targetClazz.getName());
        BsonBinaryReader bsonBinaryReader = new BsonBinaryReader(bsonInput);
        return BsonValueConverterRepertory.getBsonDocumentConverter().decode(bsonBinaryReader, targetClazz, bsonMapperConfig);
    }

    @Override
    public <T> T readFrom(String jsonString, Class<T> targetClazz) {
        if (isStrEmpty(jsonString)) {
            return null;
        }
        checkNotNull(targetClazz, NOT_NULL_MSG);
        checkIsSupportClazz(targetClazz, NOT_SUPPORT_CLAZZ_MSG + targetClazz.getName());
        JsonReader jsonReader = new JsonReader(jsonString);
        return BsonValueConverterRepertory.getBsonDocumentConverter().decode(jsonReader, targetClazz, bsonMapperConfig);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T readFrom(Document document, Class<T> target) {
        BsonDocument bsonDocument = document.toBsonDocument(target, CodecRegistries.fromCodecs(new DocumentCodec()));
        return (T) TDocument.fromBsonDocument(bsonDocument, target).getEquivalentPOJO(this);
    }

    @Override
    public BsonDocument writeToBsonDocument(Object object) {
        if (object == null) {
            return null;
        }
        BsonDocument bsonDocument = new BsonDocument();
        BsonValueConverterRepertory.getBsonDocumentConverter().encode(bsonDocument, object, bsonMapperConfig);
        return bsonDocument;
    }

    @Override
    public Document writeToMongoDocument(Object obj) {
        if (obj == null) {
            return null;
        }
        BsonDocument bsonDocument = new BsonDocument();
        BsonValueConverterRepertory.getBsonDocumentConverter().encode(bsonDocument, obj, bsonMapperConfig);
        Document document = new Document();
        for (Entry<String, BsonValue> entry : bsonDocument.entrySet()) {
            document.put(entry.getKey(), entry.getValue());
        }
        return document;
    }

    @Override
    public BsonOutput writeToBsonOutput(Object object) {
        if (object == null) {
            return null;
        }
        BsonOutput bsonOutput = new BasicOutputBuffer();
        BsonWriter bsonBinaryWriter = new BsonBinaryWriter(new BsonWriterSettings(bsonMapperConfig.getMaxMapperLayerNum()), new BsonBinaryWriterSettings(), bsonOutput);
        BsonValueConverterRepertory.getBsonDocumentConverter().encode(bsonBinaryWriter, object);
        return bsonOutput;
    }

    @Override
    public String writeAsJsonStr(Object object) {
        if (object == null) {
            return null;
        }
        StringWriter writer = new StringWriter();
        BsonWriter bsonWriter = new JsonWriter(writer, new JsonWriterSettings());
        BsonValueConverterRepertory.getBsonDocumentConverter().encode(bsonWriter, object);
        return writer.toString();
    }

}
