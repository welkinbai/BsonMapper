package me.welkinbai.bsonmapper;

import me.welkinbai.bsonmapper.exception.BsonMapperConverterException;
import org.bson.BsonDocument;
import org.bson.BsonValue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import static me.welkinbai.bsonmapper.Utils.getBsonName;
import static me.welkinbai.bsonmapper.Utils.isIgnored;

/**
 * Created by baixiaoxuan on 2017/3/23.
 */
class BsonDocumentConverter {

    private BsonDocumentConverter() {
    }

    static BsonDocumentConverter getInstance() {
        return new BsonDocumentConverter();
    }

    <T> T decode(BsonDocument bsonDocument, Class<T> targetClazz) {
        if (BsonValueConverterRepertory.isValueSupportClazz(targetClazz)) {
            throw new BsonMapperConverterException("targetClazz should not be a common value Clazz.It should be a real Object.clazz name:" + targetClazz.getName());
        }
        if (bsonDocument == null) {
            return null;
        }

        List<Field> allField = Utils.getAllField(targetClazz);
        T target = Utils.newInstanceByClazz(targetClazz);
        for (Field field : allField) {
            if (isIgnored(field)) {
                continue;
            }
            BsonValue bsonValue = bsonDocument.get(getBsonName(field));
            if (bsonValue == null) {
                continue;
            }
            Object javaValue = getJavaValueFromBsonValue(bsonValue, field);
            if (Modifier.isPrivate(field.getModifiers())) {
                field.setAccessible(true);
            }
            try {
                field.set(target, javaValue);
            } catch (IllegalAccessException e1) {
                try {
                    setBySetter(targetClazz.getDeclaredMethod(Utils.makeSetterName(field.getName())), target, javaValue);
                } catch (NoSuchMethodException e) {
                    throw new BsonMapperConverterException("need Setter for field:" + field.getName());
                }
            }
        }
        return target;
    }

    private <T> void setBySetter(Method setter, T target, Object javaValue) {
        Utils.methodInvoke(setter, target, javaValue);
    }

    private Object getJavaValueFromBsonValue(BsonValue bsonValue, Field field) {
        if (bsonValue.isArray()) {
            return BsonValueConverterRepertory.getBsonArrayConverter().decode(bsonValue.asArray(), field);
        }
        if (bsonValue.isDocument()) {
            return BsonValueConverterRepertory.getBsonDocumentConverter().decode(bsonValue.asDocument(), field.getType());
        }
        return BsonValueConverterRepertory.getConverterByBsonType(bsonValue.getBsonType()).decode(bsonValue);
    }

}
