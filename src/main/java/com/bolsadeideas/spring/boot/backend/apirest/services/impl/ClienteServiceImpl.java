package com.bolsadeideas.spring.boot.backend.apirest.services.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.spring.boot.backend.apirest.models.entity.Cliente;
import com.bolsadeideas.spring.boot.backend.apirest.models.entity.Factura;
import com.bolsadeideas.spring.boot.backend.apirest.models.entity.Producto;
import com.bolsadeideas.spring.boot.backend.apirest.models.entity.Region;
import com.bolsadeideas.spring.boot.backend.apirest.repository.ClienteRepository;
import com.bolsadeideas.spring.boot.backend.apirest.repository.FacturaRepository;
import com.bolsadeideas.spring.boot.backend.apirest.repository.ProductoRepository;
import com.bolsadeideas.spring.boot.backend.apirest.services.IClienteService;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private ClienteRepository clienteRepo;
	
	@Autowired
	private FacturaRepository facturaRepo;
	
	@Autowired
	private ProductoRepository productoRepo;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteRepo.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAllPage(Pageable pageable) {
		return clienteRepo.findAll(pageable);
	}
	
	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		return clienteRepo.save(cliente);
	}

	
	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		return clienteRepo.findById(id).orElse(null);
	}

	 
	@Override
	public void delete(Long id) {
		clienteRepo.deleteById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Region> findAllRegiones() {
		return clienteRepo.findAllRegiones();
	}

	 
	@Override
	@Transactional(readOnly = true)
	public Factura findFacturaById(Long id) {
		/**si no existe retorna null*/
		return facturaRepo.findById(id).orElse(null);
	}

 
	@Override
	@Transactional
	public Factura saveFactura(Factura factura) {
		return facturaRepo.save(factura);
	}

	
	@Override
	@Transactional
	public void eliminarFactura(Long id) {
		facturaRepo.deleteById(id);
		
	}

	 
	@Override
	@Transactional(readOnly = true)
	public List<Producto> findProductoByNombre(String dato) {
		return productoRepo.findByNombre(dato);
	}


}
