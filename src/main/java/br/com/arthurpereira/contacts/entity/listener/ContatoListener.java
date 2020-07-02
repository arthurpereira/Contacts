package br.com.arthurpereira.contacts.entity.listener;

import br.com.arthurpereira.contacts.entity.Contato;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class ContatoListener {

    @PrePersist
    @PreUpdate
    public void formatarNomeCompleto(final Contato contato) {
        contato.setNomeCompleto();
    }

}
