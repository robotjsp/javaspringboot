package co.edu.iudigital.helpmeiud.dtos.casos;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
public class CasoRequestDTO implements Serializable {

    static final long serialVersionUID = 1L;

    LocalDateTime fechaHora; // cuando ocurrió

    Float latitud;

    Float longitud;

    Float altitud;

    String descripcion;

    String urlMapa;

    String rmiUrl;

    @NotNull(message = "usuario ID requerido")
    Long usuarioId;

    @NotNull(message = "delito Id requerido")
    Long delitoId;
}