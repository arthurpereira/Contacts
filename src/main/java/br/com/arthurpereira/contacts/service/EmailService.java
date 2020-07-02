package br.com.arthurpereira.contacts.service;

import br.com.arthurpereira.contacts.entity.Contato;
import br.com.arthurpereira.contacts.entity.Email;
import br.com.arthurpereira.contacts.repository.Repository;

import java.util.List;

public interface EmailService extends Repository<Email> {

    List<Email> listAllEmailsDoContato(Contato contato);

}
