package org.moviles.exception;

public class ExepcionUsuario extends Exception {


    public ExepcionUsuario(String message) {
        super(message);
    }

    public ExepcionUsuario(String message, Throwable cause) {
        super(message, cause);
    }

    public ExepcionUsuario(Throwable cause) {
        super(cause);
    }

    public ExepcionUsuario(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
