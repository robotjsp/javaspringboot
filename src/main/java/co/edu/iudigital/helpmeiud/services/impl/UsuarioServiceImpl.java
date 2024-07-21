package co.edu.iudigital.helpmeiud.services.impl;

import co.edu.iudigital.helpmeiud.dtos.usuarios.UsuarioRequestDTO;
import co.edu.iudigital.helpmeiud.dtos.usuarios.UsuarioResponseDTO;
import co.edu.iudigital.helpmeiud.exceptions.BadRequestException;
import co.edu.iudigital.helpmeiud.exceptions.ErrorDto;
import co.edu.iudigital.helpmeiud.exceptions.RestException;
import co.edu.iudigital.helpmeiud.models.Role;
import co.edu.iudigital.helpmeiud.models.Usuario;
import co.edu.iudigital.helpmeiud.repositories.IRoleRepository;
import co.edu.iudigital.helpmeiud.repositories.IUsuarioRepository;
import co.edu.iudigital.helpmeiud.services.ifaces.IEmailService;
import co.edu.iudigital.helpmeiud.services.ifaces.IUsuarioService;
import co.edu.iudigital.helpmeiud.utils.UsuarioMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class UsuarioServiceImpl implements IUsuarioService, UserDetailsService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private IEmailService emailService;

    @Override
    public List<UsuarioResponseDTO> consultarTodos() throws RestException {
        return null;
    }

    @Override
    public UsuarioResponseDTO registrar(UsuarioRequestDTO usuarioRequestDTO)  throws RestException{

        Usuario usuario = usuarioRepository.findByUsername(usuarioRequestDTO.getUsername());

        if(usuario != null) {
            throw new BadRequestException(
                    ErrorDto.builder()
                            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                            .message("Usuario ya existe")
                            .status(HttpStatus.BAD_REQUEST.value())
                            .date(LocalDateTime.now())
                            .build());
        }

        
        usuario = usuarioMapper.toUsuario(usuarioRequestDTO);
        usuario.setRoles(Collections.singletonList(new Role(2L)));
        usuario.setEnabled(true);

        usuario = usuarioRepository.save(usuario);

        if(usuario != null) {
           if(emailService.sendMail(
                    "Hola " + usuario.getNombre() + " Te has dado de alta en HelpMe IUD con el usuario " + usuario.getUsername(),
                    usuario.getUsername(),
                    "Registro exitoso en Helpme IUD"
            ))
           {
               log.info("Mensaje enviado");
           }else{
               log.warn("Mensaje no enviado");
           }
        }

        return usuarioMapper.usuarioResponseDTO(usuario);
    }

    @Override
    public UsuarioResponseDTO consultarPorID(Long id)  throws RestException{
        return null;
    }

    @Override
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public UsuarioResponseDTO consultarPorUsername(String username)  throws RestException {
        return null;
    }

    @Override
    public UsuarioResponseDTO Actualizar(String username) throws RestException {
        return null;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if(usuario == null) {
            log.error("Usuario no existe: " + username);
            throw new UsernameNotFoundException("Usuario no existe: " + username);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        for(Role role : usuario.getRoles()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getNombre());
            authorities.add(grantedAuthority);
        }

        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getEnabled(),
                true,
                true,
                true,
                authorities);
    }
}