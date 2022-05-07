package com.bolsadeideas.spring.boot.backend.apirest.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.bolsadeideas.spring.boot.backend.apirest.models.entity.Usuario;
import com.bolsadeideas.spring.boot.backend.apirest.services.IUsuarioService;

@Component
public class InfoAdicinalToken implements TokenEnhancer{
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		/**si queremos agregar al token informacion del usuario como: nombre, apellido, email,
		 * debemos implementar una consulta q retorne el usuario por el username que lo tenemos en el token, y en esta misma clase infoAdicinalToken inyectarla,
		 * mediante el service q implementa el repositorio.
		 * 
		 * el username lo sacamos del authentication que viene como parametro*/
		
		Usuario usu = usuarioService.buscarUsuarioPorUsername(authentication.getName());
		
		Map<String, Object> info = new HashMap<>();
		info.put("info_adicional", "Hola q tal!".concat(authentication.getName()));
		info.put("nombre_username",usu.getId() +" : "+ usu.getUsername());
		info.put("nombre_usuario",usu.getNombre());
		info.put("apellido_usuario",usu.getApellido());
		info.put("email_usuario",usu.getEmail());
		
		/**hacemos un cast encerrando con () a accessToken, ya que es del tipo de la interface OAuth2AccessToken,
		 * el accessToken se convierte al tipo concreto, y ahora si se puede utilizar el método setAdditionalInformation()*/
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		
		return accessToken;
	}

}
