package com.uniandes.ecos.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Clase utilitaria para cargar archivos al servidor
 *
 * @author Daniel Arévalo
 *
 */
public class FileUploader {

	/**
	 * Guarda documentos en el servidor
	 * @param fileName
	 * @param pathFile
	 * @param input
	 * @return
	 * @throws Exception
	 */
    public static String guardarArchivoEnServidor(String fileName, String pathFile, InputStream input)
            throws Exception {
        OutputStream out = null;
        String pathFileName;
        int count = 1;
        File file;
        File folder = new File(pathFile);

        do {
            pathFileName = pathFile + fileName;
            file = new File(pathFileName);
            fileName = fileName.substring(0, fileName.lastIndexOf(".")) + (count++)
                    + fileName.substring(fileName.lastIndexOf("."));
        } while (file.exists());

        try {

            if (!folder.exists()) {
                folder.mkdirs();
            }

            out = new FileOutputStream(pathFileName);

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = input.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } finally {
            if (out != null) {
                out.close();
            }
            if (input != null) {
                input.close();
            }
        }

        return pathFileName;
    }

    /**
     * retorna todos los archivos de la carpeta seleccionada
     * @param folderPath
     * @return
     */
    public static List<File> obtenerListaArchivos(String folderPath) {
        File folder = new File(folderPath);
        return Arrays.asList(folder.listFiles());
    }
    
    /**
     * Retorna el archivo
     * @param path ruta del archivo
     * @return
     */
    public static File obtenerArchivo(String path) {
        File archivo = new File(path);
        return archivo;
    }
    
    public static boolean existeArchivo(String pathFile){
    	File folder = new File(pathFile);
    	return folder.exists();
    }
}
