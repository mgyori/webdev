package hu.unideb.webdev.exceptions;

public class UnknownFilmException extends Exception {
    public UnknownFilmException() {
    }

    public UnknownFilmException(String message) {
        super(message);
    }
}
