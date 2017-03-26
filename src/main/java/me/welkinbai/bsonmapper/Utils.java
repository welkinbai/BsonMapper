package me.welkinbai.bsonmapper;

import me.welkinbai.bsonmapper.annotations.BsonField;
import me.welkinbai.bsonmapper.annotations.BsonIgnore;
import me.welkinbai.bsonmapper.exception.BsonMapperConverterException;
import org.bson.types.ObjectId;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by baixiaoxuan on 2017/3/23.
 */
class Utils {
    private static final String SETTER_PREFIX = "set";

    private Utils() {
    }

    public static String makeSetterName(String name) {
        return SETTER_PREFIX + name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public static void checkNotNull(Object object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
    }

    public static List<Field> getAllField(Class<?> tClass) {
        Field[] tClassDeclaredFields = tClass.getDeclaredFields();
        List<Field> result = new ArrayList<Field>();
        result.addAll(Arrays.asList(tClassDeclaredFields));
        Class<?> superClass;
        while ((superClass = tClass.getSuperclass()) != null) {
            for (Field field : superClass.getDeclaredFields()) {
                int modifiers = field.getModifiers();
                if (Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers)) {
                    result.add(field);
                }
            }
            tClass = superClass;
        }
        return result;
    }

    public static <T> T newInstanceByClazz(Class<T> targetClazz) {
        try {
            return targetClazz.newInstance();
        } catch (InstantiationException e) {
            throw new BsonMapperConverterException("should never happen.", e);
        } catch (IllegalAccessException e) {
            throw new BsonMapperConverterException("IllegalAccessException.", e);
        }
    }

    public static void methodInvoke(Method method, Object object, Object value) {
        try {
            method.invoke(object, value);
        } catch (IllegalAccessException e) {
            throw new BsonMapperConverterException("IllegalAccessException.", e);
        } catch (InvocationTargetException e) {
            throw new BsonMapperConverterException("InvocationTargetException.", e);
        }
    }

    public static Class<?> giveImplClassIfSupport(Class<?> fieldType) {
        if (List.class.isAssignableFrom(fieldType)) {
            return ArrayList.class;
        }
        if (Set.class.isAssignableFrom(fieldType)) {
            return HashSet.class;
        }
        return fieldType;
    }

    public static boolean isUnableAddCollectionClazz(Class<?> fieldType) {
        return fieldType.isInterface() || Modifier.isAbstract(fieldType.getModifiers());
    }

    public static String getBsonName(Field field) {
        String bsonName = field.getName();
        BsonField bsonField = field.getAnnotation(BsonField.class);
        if (bsonField != null && !isStrEmpty(bsonField.value())) {
            bsonName = bsonField.value();
        }
        return bsonName;
    }

    public static boolean isStrEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isIgnored(Field field) {
        BsonIgnore bsonIgnore = field.getAnnotation(BsonIgnore.class);
        return bsonIgnore != null;
    }

    public static boolean fieldIsObjectId(Field field) {
        BsonField annotation = field.getAnnotation(BsonField.class);
        return annotation != null && annotation.IsObjectId();
    }

    public static Object getObjectIdByRealType(Class<?> fieldType, ObjectId objectId) {
        if (fieldType == String.class) {
            return objectId.toHexString();
        } else if (fieldType == ObjectId.class) {
            return objectId;
        } else {
            throw new BsonMapperConverterException("BsonValue ObjectId just can be converted to String or ObjectId.");
        }
    }
}
