package com.uniandes.ecos.facade;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;

import com.uniandes.ecos.dtos.CorreoElectronicoDto;
import com.uniandes.ecos.dtos.DocumentoTramiteDto;
import com.uniandes.ecos.facadeInterface.ITramitesFacade;
import com.uniandes.ecos.util.Constantes;
import com.uniandes.ecos.util.FileUploader;
import com.uniandes.ecos.util.JavaMailSender;
import com.uniandes.ecos.util.NegocioException;

@Stateless
public class TramitesFacade implements ITramitesFacade {
	
	private static Logger log = Logger.getLogger(TramitesFacade.class.getName());

	@Override
	public List<DocumentoTramiteDto> cargarArchivoTramite(Long tramiteId, String nombreArchivo, String rutaContexto, InputStream data)
			throws NegocioException {
//		String rutaCompleta = rutaContexto + Constantes.CARPETA_DOCUMENTOS_TRAMITE + tramiteId.toString() + "\\";
		String rutaCompleta = rutaContexto + tramiteId.toString() + "\\";
		try {
			FileUploader.guardarArchivoEnServidor(nombreArchivo, rutaCompleta, data);
		} catch (Exception e) {
			log.log(Level.WARNING, e.getMessage());
			throw new NegocioException('E', Constantes.CODIGO_ERROR_CARGUE_ARCHIVO,
					"Se ha presentado un error al cargar el archivo");
		}
		
		List<DocumentoTramiteDto> listaDocumentos = new ArrayList<>();
		long cont = 0;
		for(File f : FileUploader.obtenerListaArchivos(rutaCompleta)){
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

	@Override
	public void enviarCorreo(CorreoElectronicoDto correoElectronicoDto) throws NegocioException {
		try {
			JavaMailSender.enviarCorreo(correoElectronicoDto);
		} catch (Exception e) {
			e.printStackTrace();
			log.log(Level.WARNING, e.getMessage());
			throw new NegocioException('E', Constantes.CODIGO_ERROR_ENVIO_CORREO,
					"Se ha presentado un error al enviar el correo");
		}
		
	}

}
