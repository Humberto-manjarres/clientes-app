package com.bolsadeideas.spring.boot.backend.apirest.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	
	/**Esta Clase recibe y retorna el TOKEN*/
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private InfoAdicinalToken infoAdicinalToken;
	
	/**@Qualifier("authenticationManager") es el nombre del método que estamos inyectando, que está en SpringSecurityConfig*/
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;
	
	/**en este método se configuran los permisos de nuestros endpoint */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		/**tokenKeyAccess: cualquier usuario puede autenticarse consumiendo el endPoint /oauth/token/ que sería la ruta para iniciar sesion.
		 * checkTokenAccess: valida el Token que se está enviando, solo usuarios autorizados*/
		security.tokenKeyAccess("permitAll()")
		.checkTokenAccess("isAuthenticated()");
	}

	/**en este método registramos los FRONT que van acceder a nuestras APIs REST*/
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		/**tipo de almacenamiento inMemory: indicamos el cliente front.
		 * secret: es la contraseña del cliente front, la cual encriptamos.
		 * scope: es el permiso o alcance que tendrá el cliente front, lectura y escritura.
		 * 
		 * authorizedGrantTypes: es el tipo de concesion de nuestra autenticacón, es decir como vamos a obtener el TOKEN, en este caso será con password,
		 * utilizamos password cuando el usuario existe en BD y por ende utilizamos un usuario y contraseña,
		 * enviaremos las credenciales del front y las del usuario
		 * tenemos otro tipo de concesión que es refresh_token,
		 * que nos permite un token de acceso actualizado, para acceder a las diversas páginas, sin tener que iniciar sesion nuevamente.
		 * 
		 * accessTokenValiditySeconds: colocamos el tiempo en el que caduca el token password*/
		clients.inMemory().withClient("angularapp")
		.secret(passwordEncoder.encode( "12345"))
		.scopes("read","write")
		.authorizedGrantTypes("password","refresh_token")
		.accessTokenValiditySeconds(3600)/**24 horas*/
		.refreshTokenValiditySeconds(3600);
	}
	 
	/**este método se encarga de todo el proceso de autenticación y validar el TOKEN*/
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		/**en este método concatenamos la información que tiene por defecto el Token con la que vayamos adicionar.
		 * 
		 * NOTA: la informacion que trae el token por defecto la obtenemos a traves del método accessTokenConverter(), q retorna dicho Token firmado.
		 * 
		 * la clase TokenEnhancerChain nos ayuda a concatenar las informacion con la del token*/
		
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicinalToken,accessTokenConverter()));
		
		endpoints.authenticationManager(authenticationManager)
		.accessTokenConverter(accessTokenConverter()).tokenEnhancer(tokenEnhancerChain);
	}
	
	@Bean 
	public  JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey(JwtConfig.RSA_PRIVADA);/** método setSigningKey() es quien firma el TOKEN para devolverlo al FRONT*/
		jwtAccessTokenConverter.setVerifierKey(JwtConfig.RSA_PUBLICA);/**método setVerifierKey() es quien verifica el TOKEN no haya sido alterado cuando viene nuevamente del FRONT*/
		return jwtAccessTokenConverter;
	}
	
	
}
