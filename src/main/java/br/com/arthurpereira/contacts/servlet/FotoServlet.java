package br.com.arthurpereira.contacts.servlet;

import br.com.arthurpereira.contacts.resource.PropertiesResource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@WebServlet("/foto/*")
public class FotoServlet extends HttpServlet {

    private static final String DIRETORIO = PropertiesResource.getResource()
            .getString("contacts.diretorio.foto_de_perfil");

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filename = request.getPathInfo().substring(1);
        File file = new File(DIRETORIO, filename);

        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");

        Files.copy(file.toPath(), response.getOutputStream());
    }

}
