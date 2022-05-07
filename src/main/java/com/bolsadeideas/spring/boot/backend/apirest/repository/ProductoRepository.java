package com.bolsadeideas.spring.boot.backend.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.spring.boot.backend.apirest.models.entity.Producto;


public interface ProductoRepository extends CrudRepository<Producto, Long> {
	
	
	@Query("select p from Producto p where p.nombre like %?1%")
	public List<Producto> findByNombre(String dato);
	
	public List<Producto> findByNombreContainingIgnoreCase(String dato);
	
	public List<Producto> findByNombreStartingWithIgnoreCase(String dato);
	
}
