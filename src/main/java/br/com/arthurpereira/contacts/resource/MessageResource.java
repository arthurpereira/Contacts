package br.com.arthurpereira.contacts.resource;

import java.util.ResourceBundle;

public class MessageResource {

    private static ResourceBundle resource;

    public static ResourceBundle getResource() {
        if (resource == null)
            resource = ResourceBundle.getBundle("br.com.arthurpereira.contacts.properties.message");

        return resource;
    }
}
