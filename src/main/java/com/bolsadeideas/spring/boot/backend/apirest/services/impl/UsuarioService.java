package com.bolsadeideas.spring.boot.backend.apirest.services.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.spring.boot.backend.apirest.models.entity.Usuario;
import com.bolsadeideas.spring.boot.backend.apirest.repository.IUsuarioRepository;
import com.bolsadeideas.spring.boot.backend.apirest.services.IUsuarioService;

@Service
public class UsuarioService implements IUsuarioService, UserDetailsService{
	
	private Logger logger = LoggerFactory.getLogger(UsuarioService.class);
	
	
	/**NOTA: podemos inyectar este service en otra clase utilizando cualquiera de las dos interfaces q implementa este service,
	 *  IUsuarioService, UserDetailsService */
	
	@Autowired
	private IUsuarioRepository usuarioDao;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		/** retornamos UserDetails q es un usuario de Spring Security*/
		Usuario usuario = usuarioDao.findByUsername(username);
		
		if (usuario == null) {
			logger.info("Error en el login:  No existe usuario '"+username+"' en el Sistema");
			throw new UsernameNotFoundException("Error en el login:  No existe usuario '"+username+"' en el Sistema");
		}
		
		/**Los authorities o roles, los obtenemos atraves del usuario con la relación @ManyToMany.
		 * los roles son de tipo Role  los casteamos a tipo GrantedAuthority 
		 * GrantedAuthority es la interface y SimpleGrantedAuthority es la implementación */
		List<GrantedAuthority> authorities = usuario.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getNombre()))
				.peek(authority -> logger.info("Role :"+authority.getAuthority()))
				.collect(Collectors.toList());
		
		
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}

	 
	@Override
	@Transactional(readOnly = true)
	public Usuario buscarUsuarioPorUsername(String username) {
		return usuarioDao.findByUsername(username);
	}

	

	 
	
	
}
