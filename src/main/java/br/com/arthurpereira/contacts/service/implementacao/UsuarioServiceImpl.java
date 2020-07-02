package br.com.arthurpereira.contacts.service.implementacao;

import br.com.arthurpereira.contacts.entity.Usuario;
import br.com.arthurpereira.contacts.repository.RepositoryImpl;
import br.com.arthurpereira.contacts.service.UsuarioService;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Stateless
public class UsuarioServiceImpl extends RepositoryImpl<Usuario> implements UsuarioService {

    /**
     * Busca um usuario do banco de dados onde o id recebido como
     * parâmetro é igual ao id do Facebook da tabela usuario.
     *
     * @param id
     * @return
     */
    @Override
    public Usuario findByFacebookId(Long id) {
        try {
            TypedQuery<Usuario> query = getEntityManager().createQuery(
                    "SELECT usuario FROM " + Usuario.class.getName()
                            + " usuario WHERE usuario.facebookId = :id", Usuario.class);
            query.setParameter("id", id);

            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
