package com.uniandes.ecos.facadeInterface;

import java.io.InputStream;
import java.util.List;

import javax.ejb.Local;

import com.uniandes.ecos.dtos.CorreoElectronicoDto;
import com.uniandes.ecos.dtos.DocumentoTramiteDto;
import com.uniandes.ecos.util.NegocioException;

@Local
public interface ITramitesFacade {
	
	List<DocumentoTramiteDto> cargarArchivoTramite(Long tramiteId, String nombreArchivo, String rutaContexto, InputStream data) throws NegocioException;
	
	void enviarCorreo(CorreoElectronicoDto correoElectronicoDto)throws NegocioException;
}
