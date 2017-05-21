package me.welkinbai.bsonmapper;

import org.bson.Document;

/**
 * Created by welkinbai on 2017/5/21.
 */
public interface MongoBsonMapper {

    Document writeToMongoDocument(Object obj);

    <T> T readFrom(Document document, Class<T> target);
}
