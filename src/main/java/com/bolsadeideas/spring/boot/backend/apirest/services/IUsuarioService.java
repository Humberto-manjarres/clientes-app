package com.bolsadeideas.spring.boot.backend.apirest.services;

import com.bolsadeideas.spring.boot.backend.apirest.models.entity.Usuario;

public interface IUsuarioService {
	public Usuario buscarUsuarioPorUsername(String username);
}
