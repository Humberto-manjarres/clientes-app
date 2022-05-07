package com.bolsadeideas.spring.boot.backend.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bolsadeideas.spring.boot.backend.apirest.models.entity.Cliente;
import com.bolsadeideas.spring.boot.backend.apirest.models.entity.Region;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	/**de esta manera se puede abreviar una consulta JPA, para que retorne todas las regiones,
	 * la consulta es atraves de clases y no de tablas*/
	@Query("from Region")
	public List<Region> findAllRegiones();
	
}
