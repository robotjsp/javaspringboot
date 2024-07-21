package co.edu.iudigital.helpmeiud.controllers;

import co.edu.iudigital.helpmeiud.dtos.usuarios.UsuarioRequestDTO;
import co.edu.iudigital.helpmeiud.dtos.usuarios.UsuarioResponseDTO;
import co.edu.iudigital.helpmeiud.exceptions.RestException;
import co.edu.iudigital.helpmeiud.services.ifaces.IUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Usuarios Controller", description = "Controlador para gestión de Usuarios")
@RestController
@RequestMapping("/usuarios")
@Slf4j
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "500", description = "Internal Error Server")
            }
    )
    @Operation(
            summary = "SigUp en HelpMe",
            description = "Endpoint para darse de alta en el sistema"
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public ResponseEntity<UsuarioResponseDTO> register(
            @Valid @RequestBody UsuarioRequestDTO request
    ) throws RestException {
        log.info("Ejecutando create de CasoController");
        final String passwordEncoded = passwordEncoder.encode(request.getPassword());
        request.setPassword(passwordEncoded);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.registrar(request));
    }
}