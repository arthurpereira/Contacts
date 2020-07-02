package br.com.arthurpereira.contacts.service.implementacao;

import br.com.arthurpereira.contacts.entity.Contato;
import br.com.arthurpereira.contacts.entity.Usuario;
import br.com.arthurpereira.contacts.repository.RepositoryImpl;
import br.com.arthurpereira.contacts.service.ContatoService;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ContatoServiceImpl extends RepositoryImpl<Contato> implements ContatoService {

    /**
     * Retorna a lista de contatos de um usuário.
     *
     * @param usuario
     * @return
     */
    @Override
    public List<Contato> listAllContatosDoUsuario(Usuario usuario) {
        try {
            TypedQuery<Contato> query = getEntityManager().createQuery(
                    "SELECT c FROM " + Contato.class.getName() + " c"
                            + " WHERE c.usuario = :usuario"
                            + " ORDER BY c.nomeCompleto", Contato.class);
            query.setParameter("usuario", usuario);

            return query.getResultList();
        } catch (NoResultException ex) {
            return new ArrayList<>();
        }
    }

    /**
     * Busca contato no banco de dados pelo nome ou sobrenome.
     *
     * @param param
     * @param usuario
     * @return
     */
    @Override
    public List<Contato> buscarContatosDoUsuarioPeloNome(String param, Usuario usuario) {
        TypedQuery<Contato> query = getEntityManager().createQuery(
                "SELECT c FROM " + Contato.class.getName() + " c"
                        + " WHERE UPPER(c.nomeCompleto) LIKE :busca" + " AND c.usuario = :usuario"
                        + " ORDER BY c.nomeCompleto", Contato.class);
        query.setParameter("busca", "%" + param.toUpperCase() + "%");
        query.setParameter("usuario", usuario);

        return query.getResultList();
    }
}
