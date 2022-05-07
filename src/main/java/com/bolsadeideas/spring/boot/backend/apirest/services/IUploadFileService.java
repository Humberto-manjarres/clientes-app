package com.bolsadeideas.spring.boot.backend.apirest.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author <a href="mailto:totopercusion@mail.com">Humberto Manjarresr</a>
 * @project spring-boot-backend-apirest
 * @class IUploadFileService
 * @description 
 * @HU_CU_REQ 
 * @date 21 mar. 2022
 */
public interface IUploadFileService {
	/**método para mostrar la imagen*/
	public Resource cargar(String nombreFoto)throws MalformedURLException;
	
	/**método para copiar o guardar la imagen en el servidor carpeta --> uploads*/
	public String copiar(MultipartFile archivo)throws IOException;
	
	/**método para obtener la ruta donde se guarda la imagen*/
	public Path getPath(String nombreFoto);
	
	/**eliminar foto*/
	public boolean eliminar(String nombreFoto);
}
