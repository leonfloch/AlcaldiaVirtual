package com.uniandes.ecos.facade;

import java.io.InputStream;

import javax.ejb.Stateless;

import com.uniandes.ecos.facadeInterface.ITramitesFacade;
import com.uniandes.ecos.util.Constantes;
import com.uniandes.ecos.util.FileUploader;
import com.uniandes.ecos.util.NegocioException;

@Stateless
public class TramitesFacade implements ITramitesFacade {

	@Override
	public void cargarArchivoTramite(Long tramiteId, String nombreArchivo, String rutaContexto, InputStream data)
			throws NegocioException {
		String rutaCompleta = rutaContexto + Constantes.CARPETA_DOCUMENTOS_TRAMITE + tramiteId.toString() + "\\";
		try {
			System.out.println("Guardando documento");
			FileUploader.guardarArchivoEnServidor(nombreArchivo, rutaCompleta, data);
		} catch (Exception e) {
			throw new NegocioException("E", Constantes.CODIGO_ERROR_CARGUE_ARCHIVO,
					"Se ha presentado un error al cargar el archivo");
		}
	}

}
