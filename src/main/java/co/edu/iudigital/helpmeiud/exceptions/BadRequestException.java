package co.edu.iudigital.helpmeiud.exceptions;
// Exception de BadRequest en el Rest

public class BadRequestException extends RestException { // 400

    private static final long serialVersionUID = 1L;

    public BadRequestException() {
        super();
    }

    public BadRequestException(ErrorDto errorDto) {
        super(errorDto);
    }

    public BadRequestException(String msg) {
        super(msg);
    }
}