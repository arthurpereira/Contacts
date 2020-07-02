package br.com.arthurpereira.contacts.servlet;

import br.com.arthurpereira.contacts.entity.Usuario;
import br.com.arthurpereira.contacts.service.UsuarioService;
import br.com.arthurpereira.contacts.resource.FacebookResource;
import br.com.arthurpereira.contacts.util.ServletUtil;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.concurrent.ExecutionException;

@WebServlet(urlPatterns = "/callback/facebook", asyncSupported = true)
public class FacebookCallbackServlet extends HttpServlet {

    @EJB
    private UsuarioService usuarioService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String error = req.getParameter("error");

        if ((null != error) && (error.trim()).equals("access_denied")) {
            HttpSession session = req.getSession();
            session.invalidate();
            resp.sendRedirect(req.getContextPath());

            return;
        }
        AsyncContext asyncContext = req.startAsync();
        asyncContext.start(new GetUserInfo(req, resp, asyncContext));
    }

    class GetUserInfo implements Runnable {
        private HttpServletRequest req;
        private HttpServletResponse resp;
        private AsyncContext asyncContext;

        public GetUserInfo(HttpServletRequest req, HttpServletResponse resp, AsyncContext asyncContext) {
            this.req = req;
            this.resp = resp;
            this.asyncContext = asyncContext;
        }

        @Override
        public void run() {
            HttpSession session = req.getSession();
            OAuth20Service service = (OAuth20Service) session.getAttribute("oauth2service");

            String code = req.getParameter("code");
            try {
                OAuth2AccessToken accessToken = service.getAccessToken(code);
                session.setAttribute("token", accessToken);

                OAuthRequest oAuthRequest = new OAuthRequest(Verb.GET,
                        FacebookResource.getResource().getString("social.facebook.private_resource_url"));
                service.signRequest(accessToken, oAuthRequest);

                Response oAuthResponse = service.execute(oAuthRequest);

                if (oAuthResponse.isSuccessful()) {
                    JsonObject jsonObject = Json.createReader(new StringReader(oAuthResponse.getBody())).readObject();

                    System.out.println(jsonObject.getString("name"));
                    System.out.println(jsonObject.getString("id"));

                    Long facebookId = Long.parseLong(jsonObject.getString("id"));

                    Usuario usuario = usuarioService.findByFacebookId(facebookId);

                    if (usuario != null) {
                        usuario.setDataUltimoLogin(new Date());
                        usuarioService.update(usuario);
                    } else {
                        usuario = new Usuario();
                        usuario.setFacebookId(facebookId);
                        usuario.setDataCriacao(new Date());
                        usuarioService.save(usuario);
                    }

                    session.setAttribute("usuario", usuario);

                    resp.sendRedirect(ServletUtil.getURLWithContextPath(req) + "/contatos");
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            asyncContext.complete();
        }
    }

}