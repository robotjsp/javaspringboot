package co.edu.iudigital.helpmeiud.exceptions;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
//------------------------------
import java.io.Serializable;
import java.time.LocalDateTime;

//Erro Dto Exception

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
public class ErrorDto implements Serializable {

    static final long serialVersionUID = 1L;

    String error;

    String message;

    int status;

    LocalDateTime date;

    //Nuevo error
    
    public static ErrorDto getErrorDto(String error, String message, int status) {
        return ErrorDto.builder()
                .error(error)
                .message(message)
                .status(status)
                .date(LocalDateTime.now())
                .build();
    }
}