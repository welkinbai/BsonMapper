package me.welkinbai.bsonmapper.exception;

/**
 * Created by welkinbai on 2017/3/22.
 */
public class BsonMapperConverterException extends RuntimeException {

    public BsonMapperConverterException() {
        super();
    }

    public BsonMapperConverterException(String message) {
        super(message);
    }

    public BsonMapperConverterException(String message, Throwable cause) {
        super(message, cause);
    }

    public BsonMapperConverterException(Throwable cause) {
        super(cause);
    }

}
