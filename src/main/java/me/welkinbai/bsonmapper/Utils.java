package me.welkinbai.bsonmapper;

import me.welkinbai.bsonmapper.annotations.BsonArrayField;
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
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.sun.javafx.fxml.BeanAdapter.IS_PREFIX;

/**
 * Created by baixiaoxuan on 2017/3/23.
 */
final class Utils {
    private static final String SETTER_PREFIX = "set";
    private static final String GETTER_PREFIX = "get";

    private Utils() {
    }

    public static String makeSetterName(String name) {
        return SETTER_PREFIX + name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public static String makeGetterName(String name) {
        return GETTER_PREFIX + name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    private static String makeIsGetterName(String name) {
        return IS_PREFIX + name.substring(0, 1).toUpperCase() + name.substring(1);
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
        if (fieldType == String.class || fieldType == StringObjectId.class) {
            return objectId.toHexString();
        } else if (fieldType == ObjectId.class) {
            return objectId;
        } else {
            throw new BsonMapperConverterException("BsonValue ObjectId just can be converted to String or ObjectId.");
        }
    }

    public static void checkIsSupportClazz(Class<?> targetClazz, String message) {
        if (BsonValueConverterRepertory.isValueSupportClazz(targetClazz)) {
            throw new BsonMapperConverterException(message);
        }
    }

    public static Object getFieldValue(Field field, Object object) {
        Class<?> clazz = object.getClass();
        Object value;
        String getterName = null;
        try {
            Class<?> fieldType = field.getType();
            boolean isBoolField = fieldType == Boolean.class || fieldType == boolean.class;
            if (isBoolField) {
                getterName = Utils.makeIsGetterName(field.getName());
            } else {
                getterName = Utils.makeGetterName(field.getName());
            }
            Method getter = clazz.getDeclaredMethod(getterName);
            value = getter.invoke(object);
        } catch (NoSuchMethodException e) {
            if (Modifier.isPrivate(field.getModifiers())) {
                field.setAccessible(true);
            }
            try {
                value = field.get(object);
            } catch (IllegalAccessException e1) {
                throw new BsonMapperConverterException("IllegalAccessException. field name:" + field.getName(), e);
            }
        } catch (InvocationTargetException e) {
            throw new BsonMapperConverterException("InvocationTargetException. getterName:" + getterName, e);
        } catch (IllegalAccessException e) {
            throw new BsonMapperConverterException("IllegalAccessException. getterName:" + getterName, e);
        }
        return value;
    }

    public static boolean isArrayType(Class<?> fieldType) {
        return fieldType.isArray() || Collection.class.isAssignableFrom(fieldType);
    }

    public static BsonArrayField getBsonArrayFieldAnnotation(Field field) {
        BsonArrayField bsonArrayField = field.getAnnotation(BsonArrayField.class);
        if (bsonArrayField == null) {
            throw new BsonMapperConverterException(String.format("field %s need @BsonArrayField for Collection field.If you don't want to decode the field, add @BsonIgnore for it.", field.getName()));
        }
        return bsonArrayField;
    }
}
