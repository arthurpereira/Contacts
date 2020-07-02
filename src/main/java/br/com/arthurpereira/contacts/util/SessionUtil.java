package br.com.arthurpereira.contacts.util;

import br.com.arthurpereira.contacts.entity.Usuario;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class SessionUtil {

    public static Usuario getUsuario() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        return (Usuario) request.getSession().getAttribute("usuario");
    }

}
