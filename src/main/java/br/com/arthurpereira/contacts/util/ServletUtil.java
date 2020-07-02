package br.com.arthurpereira.contacts.util;

import javax.servlet.http.HttpServletRequest;

public class ServletUtil {

    public static String getURLWithContextPath(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
