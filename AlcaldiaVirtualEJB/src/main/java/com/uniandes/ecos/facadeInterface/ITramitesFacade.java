package com.uniandes.ecos.facadeInterface;

import java.io.InputStream;

import javax.ejb.Local;

import com.uniandes.ecos.util.NegocioException;

@Local
public interface ITramitesFacade {
	void cargarArchivoTramite(Long tramiteId, String nombreArchivo, String rutaContexto, InputStream data) throws NegocioException;
}
