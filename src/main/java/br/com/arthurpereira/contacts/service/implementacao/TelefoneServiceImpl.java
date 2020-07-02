package br.com.arthurpereira.contacts.service.implementacao;

import br.com.arthurpereira.contacts.entity.Contato;
import br.com.arthurpereira.contacts.entity.Telefone;
import br.com.arthurpereira.contacts.repository.RepositoryImpl;
import br.com.arthurpereira.contacts.service.TelefoneService;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class TelefoneServiceImpl extends RepositoryImpl<Telefone> implements TelefoneService {

    /**
     * Retorna a lista de telefones de um contato.
     *
     * @param contato
     * @return
     */
    @Override
    public List<Telefone> listAllTelefonesDoContato(Contato contato) {
        TypedQuery<Telefone> query = getEntityManager().createQuery(
                "SELECT t FROM " + Telefone.class.getName() + " t"
                        + " WHERE t.contato = :contato"
                        + " ORDER BY t.id", Telefone.class);
        query.setParameter("contato", contato);

        return query.getResultList();
    }

}
