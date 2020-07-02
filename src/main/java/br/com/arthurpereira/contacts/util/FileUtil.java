package br.com.arthurpereira.contacts.util;

import br.com.arthurpereira.contacts.enums.TiposDeImagemPermitidosEnum;
import br.com.arthurpereira.contacts.resource.PropertiesResource;

import javax.servlet.http.Part;

public class FileUtil {

    public static final int MAX_FILE_SIZE = Integer.parseInt(PropertiesResource.getResource()
            .getString("contacts.upload.tamanho_maximo.foto_de_perfil"));

    public static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
            }
        }
        return null;
    }

    public static String getExtension(String filename) {
        String extension = "";

        int i = filename.lastIndexOf('.');
        int p = Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'));

        if (i > p) {
            extension = filename.substring(i+1);
        }

        return extension;
    }

    public static String getType(String mimeType) {
        String type = mimeType.substring(mimeType.lastIndexOf('/') + 1, mimeType.length());

        return type;
    }

    public static boolean validateMimeType(String mimeType) {
        boolean isValid = false;

        for (TiposDeImagemPermitidosEnum validMimeType : TiposDeImagemPermitidosEnum.values()) {
            if (mimeType == validMimeType.getMimeType()) {
                isValid = true;
            }
        }

        return isValid;
    }

}
