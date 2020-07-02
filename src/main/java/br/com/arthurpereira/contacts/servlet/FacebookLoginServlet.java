package br.com.arthurpereira.contacts.servlet;

import br.com.arthurpereira.contacts.resource.FacebookResource;
import br.com.arthurpereira.contacts.util.ServletUtil;
import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login/facebook")
public class FacebookLoginServlet extends HttpServlet {

    private static final String APP_ID = FacebookResource.getResource().getString("social.facebook.app_id");
    private static final String APP_SECRET = FacebookResource.getResource().getString("social.facebook.app_secret");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OAuth20Service service = new ServiceBuilder(APP_ID)
                .apiSecret(APP_SECRET)
                .callback(ServletUtil.getURLWithContextPath(req) + "/callback/facebook")
                .build(FacebookApi.instance());

        HttpSession session = req.getSession();
        session.setAttribute("oauth2service", service);

        resp.sendRedirect(service.getAuthorizationUrl());
    }
}
