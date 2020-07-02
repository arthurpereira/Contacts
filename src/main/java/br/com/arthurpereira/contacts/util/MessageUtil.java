package br.com.arthurpereira.contacts.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MessageUtil {

    public static void showMessageInfo(String message) {
        FacesMessage facesMessage = createMessage(FacesMessage.SEVERITY_INFO, message);
        addMessageToFacesContext(facesMessage);
    }

    public static void showMessageWarn(String message) {
        FacesMessage facesMessage = createMessage(FacesMessage.SEVERITY_WARN, message);
        addMessageToFacesContext(facesMessage);
    }

    public static void showMessageError(String message) {
        FacesMessage facesMessage = createMessage(FacesMessage.SEVERITY_ERROR, message);
        addMessageToFacesContext(facesMessage);
    }

    private static FacesMessage createMessage(FacesMessage.Severity severity, String mensagemErro) {
        return new FacesMessage(severity, mensagemErro, mensagemErro);
    }

    private static void addMessageToFacesContext(FacesMessage facesMessage) {
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

}

