package com.bolsadeideas.spring.boot.backend.apirest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bolsadeideas.spring.boot.backend.apirest.models.entity.Factura;
import com.bolsadeideas.spring.boot.backend.apirest.services.IClienteService;

@CrossOrigin(origins = {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api")
public class FacturaController {
	
	@Autowired
	private IClienteService clienteservice;
	
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@GetMapping("/factura/{id}")
	public ResponseEntity<?> show(@PathVariable Long id){
		return ResponseEntity.status(HttpStatus.OK).body(clienteservice.findFacturaById(id));
	}
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/factura/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id){
		clienteservice.eliminarFactura(id);
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/facturas/filtrar-productos/{texto}")
	public ResponseEntity<?> filtrarProductos(@PathVariable String texto){
		return ResponseEntity.status(HttpStatus.OK).body(clienteservice.findProductoByNombre(texto));
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/crear/factura")
	public ResponseEntity<?> crear(@RequestBody Factura factura){
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteservice.saveFactura(factura));
	}
	
}
