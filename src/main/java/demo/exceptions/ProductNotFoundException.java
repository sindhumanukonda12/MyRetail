package demo.exceptions;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(final String message,final Throwable exe) {
        super(message,exe);
    }

    public ProductNotFoundException(String message) {
        super(message);
    }

}
