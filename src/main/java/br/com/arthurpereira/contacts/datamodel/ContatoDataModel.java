package br.com.arthurpereira.contacts.datamodel;

import br.com.arthurpereira.contacts.entity.Contato;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ContatoDataModel extends ListDataModel<Contato> implements SelectableDataModel<Contato>, Serializable {

    public ContatoDataModel() {

    }

    public ContatoDataModel(List<Contato> data) {
        super(data);
    }

    @Override
    public Object getRowKey(Contato contato) {
        return contato.getId();
    }

    @Override
    public Contato getRowData(String rowKey) {
        List<Contato> contatos = (List<Contato>) getWrappedData();

        for (Contato contato : contatos) {
            if (Objects.equals(contato.getId(), Long.valueOf(rowKey))) {
                return contato;
            }
        }

        return null;
    }
}
