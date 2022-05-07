package com.bolsadeideas.spring.boot.backend.apirest.services.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bolsadeideas.spring.boot.backend.apirest.services.IUploadFileService;

@Service
public class UploadFileServiceImpl implements IUploadFileService{
	
	private final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);
	
	private final static String DIRECTORIO_UPLOAD = "uploads";
	
	@Override
	public Resource cargar(String nombreFoto) throws MalformedURLException {
		
		/**ruta donde se guardan las fotos*/
		Path rutaArchivo = getPath(nombreFoto);
		log.info(rutaArchivo.toString());
		
		Resource recurso = null;
		
		recurso = new UrlResource(rutaArchivo.toUri());
		
		
		if (!recurso.exists()) {
			rutaArchivo = Paths.get("src/main/resources/static/images").resolve("no-usuario.jpg").toAbsolutePath();
			
			recurso = new UrlResource(rutaArchivo.toUri());
			
			//throw new RuntimeException("Error no se pudo cargar la imagen "+ nombreFoto);
			log.error("Error no se pudo cargar la imagen "+ nombreFoto);
		}
		
		return recurso;
		
	}

	 
	@Override
	public String copiar(MultipartFile archivo) throws IOException {
		/**Nombre de la foto, y que cada nombre sea diferente, y reemplazamos los espacios en nada*/
		String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ", "");
		
		/**Ruta donde vamos a subir la foto*/
		Path rutaArchivo = getPath(nombreArchivo);
		log.info(rutaArchivo.toString());
		
		/**copiamos el archivo que subimos, a la ruta escogida*/
		Files.copy(archivo.getInputStream(),rutaArchivo);
		 
		return nombreArchivo;
	}

	
	@Override
	public boolean eliminar(String nombreFoto) {
		
		if (nombreFoto != null && nombreFoto.length() > 0) {
			Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
			File archviFotoAnterior = rutaFotoAnterior.toFile();
			if (archviFotoAnterior.exists() && archviFotoAnterior.canRead()) {
				archviFotoAnterior.delete();
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public Path getPath(String nombreFoto) {
		return Paths.get(DIRECTORIO_UPLOAD).resolve(nombreFoto).toAbsolutePath();
	}


}
