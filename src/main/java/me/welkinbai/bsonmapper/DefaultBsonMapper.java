package me.welkinbai.bsonmapper;

import me.welkinbai.bsonmapper.exception.BsonMapperConverterException;
import org.bson.BsonDocument;
import org.bson.BsonDocumentReader;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.codecs.BsonValueCodecProvider;
import org.bson.io.BsonInput;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by welkinbai on 2017/3/21.
 */
public class DefaultBsonMapper implements BsonMapper {

    private static final String SETTER_PREFIX = "set";

    @Override
    public <T> T readFrom(BsonDocument bsonDocument, Class<T> targetClazz) {
        BsonReader bsonReader = new BsonDocumentReader(bsonDocument);
        return readFrom(bsonReader, targetClazz);
    }

    private <T> T readFrom(BsonReader bsonReader, Class<T> targetClazz) {
        try {
            T instance = targetClazz.newInstance();
            bsonReader.readStartDocument();
            while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                String name = bsonReader.readName();
                String methodName = makeSetterName(name);
                try {
                    Method method = targetClazz.getMethod(methodName);
                    Field field = targetClazz.getDeclaredField(name);
                    method.invoke(instance, decodeWithReader(bsonReader, field.getType()));
                } catch (NoSuchMethodException e) {
                    throw new BsonMapperConverterException("No such method:" + methodName);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
            return instance;
        } catch (InstantiationException e) {
            throw new BsonMapperConverterException("create target object fail!", e);
        } catch (IllegalAccessException e) {
            throw new BsonMapperConverterException("illegal access exception!", e);
        }
    }

    private Object readArrayFrom(BsonReader bsonReader, Class<?> fieldType) {
        List<Object> list = new ArrayList<Object>();
        bsonReader.readStartArray();
        while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            Class<?> targetClass = BsonValueCodecProvider.getBsonTypeClassMap().get(bsonReader.getCurrentBsonType());
            decodeWithReader(bsonReader, targetClass);
        }
        return null;
    }

    private Object decodeWithReader(BsonReader bsonReader, Class<?> fieldType) {
        switch (bsonReader.getCurrentBsonType()) {
            case DOUBLE:
                return bsonReader.readDouble();
            case STRING:
                return bsonReader.readString();
            case DOCUMENT:
                return readFrom(bsonReader, fieldType);
            case ARRAY:
                return readArrayFrom(bsonReader, fieldType);



        }
        return null;
    }

    private String makeSetterName(String name) {
        checkNotNull(name, "name should not be null!");
        return SETTER_PREFIX + name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    private void checkNotNull(Object object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
    }

    @Override
    public <T> T readFrom(ByteBuffer byteBuffer, Class<T> targetClazz) {
        return null;
    }

    @Override
    public <T> T readFrom(BsonInput bsonInput, Class<T> targetClazz) {
        return null;
    }

    @Override
    public <T> T readFrom(String jsonString, Class<T> targetClazz) {
        return null;
    }

    public static void main(String[] args) {
        List<String> test = new ArrayList<String>();
        Class<?> aClass = test.getClass();
        try {
            Method method = aClass.getMethod("toArray", Object[].class);
            Type type = method.getGenericParameterTypes()[0];
//            ParameterizedType parameterizedType = (ParameterizedType) type;
//            System.out.println(parameterizedType.getActualTypeArguments()[0]);
            TypeVariable<Method>[] typeParameters = method.getTypeParameters();
            System.out.println(typeParameters[0].getName());


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
