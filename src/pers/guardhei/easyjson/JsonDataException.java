package pers.guardhei.easyjson;

public class JsonDataException extends Exception {

    public JsonDataException() {
        super();
    }

    public JsonDataException(String message) {
        super(message);
    }

    public JsonDataException(Throwable cause) {
        super(cause);
    }

    public JsonDataException(String message, Throwable cause) {
        super(message, cause);
    }
}