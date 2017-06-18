package me.welkinbai.bsonmapper;

import org.bson.types.ObjectId;

/**
 * Created by welkinbai on 2017/6/18.
 */
public final class StringObjectId {
    private final ObjectId objectId;

    public StringObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public String getStringObjectId() {
        return this.objectId.toHexString();
    }

    public ObjectId getInnerObjectId() {
        return this.objectId;
    }

}
