package br.com.arthurpereira.contacts.resource;

import java.util.ResourceBundle;

public class PropertiesResource {

    private static ResourceBundle resource;

    public static ResourceBundle getResource() {
        if (resource == null)
            resource = ResourceBundle.getBundle("br.com.arthurpereira.contacts.properties.contacts");

        return resource;
    }

}
