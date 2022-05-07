package com.bolsadeideas.spring.boot.backend.apirest.auth;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
	
	/**este método nos permite implementar todas las reglas de seguridad de nuestros endPoint de la aplicación*/
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		/**rutas publicas que pueden acceder si está logueados o no*/
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/clientes","/api/clientes/page/**","/api/uploads/img/**","/images/**").permitAll()
		/*.antMatchers("/api/cliente/{id}").permitAll()
		.antMatchers("/api/facturas/**").permitAll().antMatchers("/api/factura/{id}").permitAll().antMatchers("/api/crear/factura").permitAll()*/
		
		/*.antMatchers(HttpMethod.GET,"/api/clientes/{id}").hasAnyRole("USER","ADMIN")
		.antMatchers(HttpMethod.POST,"/api/cliente/upload").hasAnyRole("USER","ADMIN")
		.antMatchers(HttpMethod.POST,"/api/cliente").hasRole("ADMIN")
		.antMatchers("/api/cliente/***").hasRole("ADMIN")*/
		.anyRequest().authenticated().and().cors().configurationSource(corsConfigurationSource());
		
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		/**permitir el dominio donde residen nuestros clientes ejemplo: agular, react, etc*/
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		
		/**configurar los métodos que vamos a permitir en nuestra aplicación en el Banckend.
		 * OPTIONS algunos navegadores lo utilizan en el momento de la autenticación*/
		configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("Content-Type","Authorization"));
		
		/**registramos la configuration del Cors para todos los endpoint del backend*/
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		
		return source;
	}
	
	/**creamos el filtro de Cors que recibe la configuración corsConfigurationSource(),
	 * se le dá la prioridad más alta con Ordered.HIGHEST_PRECEDENCE.*/
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	} 
	
}
