package com.bolsadeideas.spring.boot.backend.apirest.repository;

import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.spring.boot.backend.apirest.models.entity.Factura;


public interface FacturaRepository extends CrudRepository<Factura, Long> {

}
