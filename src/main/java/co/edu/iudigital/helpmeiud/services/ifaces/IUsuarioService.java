package co.edu.iudigital.helpmeiud.services.ifaces;

import co.edu.iudigital.helpmeiud.dtos.usuarios.UsuarioRequestDTO;
import co.edu.iudigital.helpmeiud.dtos.usuarios.UsuarioResponseDTO;
import co.edu.iudigital.helpmeiud.exceptions.RestException;
import co.edu.iudigital.helpmeiud.models.Usuario;

import java.util.List;

public interface IUsuarioService {

    List<UsuarioResponseDTO> consultarTodos() throws RestException;

    UsuarioResponseDTO registrar(UsuarioRequestDTO usuarioRequestDTO)  throws RestException;

    UsuarioResponseDTO consultarPorID(Long id)  throws RestException;

    Usuario findByUsername(String username);

    UsuarioResponseDTO consultarPorUsername(String username) throws RestException;

    UsuarioResponseDTO Actualizar(String username) throws RestException;
}