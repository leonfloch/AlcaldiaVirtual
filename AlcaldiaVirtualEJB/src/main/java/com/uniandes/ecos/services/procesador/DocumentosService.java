/**
 *
 */
package com.uniandes.ecos.services.procesador;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;

import com.uniandes.ecos.dtos.DocumentoTramiteDto;
import com.uniandes.ecos.interfaz.services.procesador.IDocumentosService;
import com.uniandes.ecos.util.Constantes;
import com.uniandes.ecos.util.FileUploader;
import com.uniandes.ecos.util.NegocioException;

/**
 * Implementacion Servicio encargado del manejo de los documentos que se cargan
 * y descargan en el sistema.
 *
 * @author 80221940
 *
 */
@Stateless
public class DocumentosService implements IDocumentosService {

	private static final String PROPERTIES_FILE = "/resources/application.properties";
	private static Logger log = Logger.getLogger(DocumentosService.class.getName());
	private Properties propiedades;
	private InputStream inputStream;


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uniandes.ecos.interfaz.services.procesador.IDocumentosService#
	 * cargarArchivoTramite(java.lang.Long, java.lang.String, java.lang.String,
	 * java.io.InputStream)
	 */
	@Override
	public List<DocumentoTramiteDto> cargarArchivoTramite(Long tramiteId, String nombreArchivo, InputStream data)
			throws NegocioException {

		cargarArchivoPropiedades();
		String rutaCompleta = propiedades.getProperty(Constantes.PROPIEDAD_CARPETA_DOCUMENTOS_TRAMITE)
				+ tramiteId.toString() + "\\";
		try {
			FileUploader.guardarArchivoEnServidor(nombreArchivo, rutaCompleta, data);
		} catch (Exception e) {
			e.printStackTrace();
			log.log(Level.WARNING, e.getMessage());
			throw new NegocioException('E', Constantes.CODIGO_ERROR_CARGUE_ARCHIVO,
					"Se ha presentado un error al cargar el archivo");
		}

		List<DocumentoTramiteDto> listaDocumentos = new ArrayList<>();
		long cont = 0;
		for (File f : FileUploader.obtenerListaArchivos(rutaCompleta)) {
			DocumentoTramiteDto temp = new DocumentoTramiteDto();
			temp.setConsecutivo(++cont);
			temp.setNombre(f.getName());
			temp.setRuta(f.getAbsolutePath());
			temp.setTramiteId(tramiteId);
			temp.setArchivo(f);

			listaDocumentos.add(temp);
		}
		return listaDocumentos;
	}

	private void cargarArchivoPropiedades() throws NegocioException {
		propiedades = new Properties();
		inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE);
		
		try {
			if (inputStream != null) {
				propiedades.load(inputStream);
			} else {
				throw new NegocioException('E', Constantes.CODIGO_ERROR_CARGUE_ARCHIVO_PROPIEDADES, "No se ha podido cargar el archivo de propiedades");
			}
		} catch (IOException e) {
			throw new NegocioException('E', Constantes.CODIGO_ERROR_CARGUE_ARCHIVO_PROPIEDADES, e.getMessage());
		}

	}

}
