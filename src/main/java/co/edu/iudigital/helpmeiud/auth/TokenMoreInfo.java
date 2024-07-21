package co.edu.iudigital.helpmeiud.auth;

import co.edu.iudigital.helpmeiud.models.Usuario;
import co.edu.iudigital.helpmeiud.services.ifaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
/**@author RobertCastillos
@author OscarGarcia
@author JosueSanmartin
@author CamiloHenao*/

@Component
public class TokenMoreInfo implements TokenEnhancer {

	@Autowired
	private IUsuarioService usuarioService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Usuario usuario = usuarioService.findByUsername(authentication.getName());
		Map<String, Object> info = new HashMap<>();
		info.put("id_usuario", ""+usuario.getId());
		info.put("nombre", usuario.getNombre());
		info.put("image", usuario.getImage());
		info.put("fecha_nacimiento", ""+usuario.getFechaNacimiento());
		((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(info);
		return accessToken;
	}

}