package com.bolsadeideas.spring.boot.backend.apirest.controllers;



import java.io.IOException;
import java.net.MalformedURLException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bolsadeideas.spring.boot.backend.apirest.models.entity.Cliente;

import com.bolsadeideas.spring.boot.backend.apirest.services.IClienteService;
import com.bolsadeideas.spring.boot.backend.apirest.services.IUploadFileService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api")
public class ClienteController {
	
	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private IUploadFileService uploadService; 
	
	private final Logger log = LoggerFactory.getLogger(ClienteController.class);
	
	@GetMapping("/clientes")
	public ResponseEntity<?> index(){
		//El código de ok es 200
		return ResponseEntity.ok(clienteService.findAll());
	}
	
	@GetMapping("/clientes/page/{page}")
	public ResponseEntity<?> index(@PathVariable Integer page){
		Pageable pages = PageRequest.of(page, 4);
		return ResponseEntity.ok(clienteService.findAllPage(pages));
	}
	
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@GetMapping("/cliente/{id}")
	public ResponseEntity<?> show(@PathVariable("id") Long id){
		Cliente cliente = null; 
		
		/**Map es la interface y HashMap es la implementación */
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			cliente = clienteService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la Base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		
		if (cliente == null) {
			response.put("mensaje", "Cliente ".concat(id.toString()).concat(" no existe en la base de datos"));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(cliente);
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/cliente")
	public ResponseEntity<?> save(@Valid @RequestBody Cliente cliente,BindingResult result){
		Cliente clienteNew = null;
		/**Map es la interface y HashMap es la implementación */
		Map<String, Object> response = new HashMap<String, Object>();
		
		if (result.hasErrors()) {
			
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(e -> {
						return "El campo "+ e.getField() +" "+ e.getDefaultMessage();
					}).collect(Collectors.toList());
					
			
			response.put("errors", errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		try {
			clienteNew = clienteService.save(cliente);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la Base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		response.put("mensaje", "El cliente ha sido creado con éxito!");
		response.put("cliente", clienteNew);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@PostMapping("/cliente/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id){
		
		System.out.println("archivo --> "+archivo.getName());
		
		/**Map es la interface y HashMap es la implementación */
		Map<String, Object> response = new HashMap<String, Object>();
		Cliente cliente = clienteService.findById(id);
		
		if (!archivo.isEmpty()) {
			String nombreArchivo = null;
			
			try {
				nombreArchivo = uploadService.copiar(archivo);
			} catch (IOException e) {
				e.printStackTrace();
				response.put("mensaje", "Error al subir la imagen ");
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				log.info(response.toString());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			}
			
			String nombreFotoAnterior = cliente.getFoto();
			uploadService.eliminar(nombreFotoAnterior);
			
			cliente.setFoto(nombreArchivo);
			clienteService.save(cliente);
			response.put("cliente", cliente);
			response.put("mensaje", "Imagen subida correctamente "+ nombreArchivo);
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	/**con este método cargamos la foto*/
	@GetMapping("/uploads/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){
	
		Resource recurso = null;
		
		try {
			recurso = uploadService.cargar(nombreFoto);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		/**para que se pueda descargar la imagen*/
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+recurso.getFilename()+"\"");
		
		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/cliente/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result,@PathVariable Long id){
		Cliente clienteActual = clienteService.findById(id);
		Cliente clienteActualizado = null;
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		if (result.hasErrors()) {
			
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(e -> {
						return "El campo "+ e.getField() +" "+ e.getDefaultMessage();
					}).collect(Collectors.toList());
					
			
			response.put("error", errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		if (clienteActual == null) {
			response.put("mensaje", "Error, no se puede editar, El cliente ".concat(id.toString()).concat(" no existe en la base de datos"));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		clienteActual.setNombre(cliente.getNombre());
		clienteActual.setApellido(cliente.getApellido());
		clienteActual.setEmail(cliente.getEmail());
		clienteActual.setCreateAt(cliente.getCreateAt());
		clienteActual.setRegion(cliente.getRegion());
		
		try {
			clienteActualizado = clienteService.save(clienteActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar en la Base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		
		response.put("mensaje", "El cliente ha sido actualizado con éxito!");
		response.put("cliente", clienteActualizado);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/cliente/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		//NO_CONTENT codigo 204
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			Cliente cliente = clienteService.findById(id);
			String nombreFotoAnterior = cliente.getFoto();
			uploadService.eliminar(nombreFotoAnterior);
			
			clienteService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en la Base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		response.put("mensaje", "El cliente ha sido eliminado con éxito!");
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/cliente/regiones")
	public ResponseEntity<?> listarRegiones(){
		//El código de ok es 200
		return ResponseEntity.ok(clienteService.findAllRegiones());
	}
}
