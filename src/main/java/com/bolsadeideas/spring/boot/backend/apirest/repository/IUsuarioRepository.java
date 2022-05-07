package com.bolsadeideas.spring.boot.backend.apirest.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.spring.boot.backend.apirest.models.entity.Usuario;

public interface IUsuarioRepository extends CrudRepository<Usuario, Long> {
	
	@Query("select u from Usuario u where u.username =?1")
	public Usuario findByUsername2(String username);
	
	public Usuario findByUsername(String username);
}
