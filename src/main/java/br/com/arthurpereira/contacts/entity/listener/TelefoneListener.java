package br.com.arthurpereira.contacts.entity.listener;

import br.com.arthurpereira.contacts.entity.Telefone;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class TelefoneListener {

    @PrePersist
    @PreUpdate
    public void formatarTelefone(final Telefone telefone) {
        telefone.setNumeroFormatado();
    }

}
