package br.com.arthurpereira.contacts.service.implementacao;

import br.com.arthurpereira.contacts.entity.Contato;
import br.com.arthurpereira.contacts.entity.Email;
import br.com.arthurpereira.contacts.repository.RepositoryImpl;
import br.com.arthurpereira.contacts.service.EmailService;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class EmailServiceImpl extends RepositoryImpl<Email> implements EmailService {

    /**
     * Retorna a lista de emails de um contato.
     *
     * @param contato
     * @return
     */
    @Override
    public List<Email> listAllEmailsDoContato(Contato contato) {
        TypedQuery<Email> query = getEntityManager().createQuery(
                "SELECT e FROM " + Email.class.getName() + " e"
                        + " WHERE e.contato = :contato"
                        + " ORDER BY e.id", Email.class);
        query.setParameter("contato", contato);

        return query.getResultList();
    }
}
