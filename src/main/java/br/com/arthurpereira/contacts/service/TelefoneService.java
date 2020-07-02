package br.com.arthurpereira.contacts.service;

import br.com.arthurpereira.contacts.entity.Contato;
import br.com.arthurpereira.contacts.entity.Telefone;
import br.com.arthurpereira.contacts.repository.Repository;

import java.util.List;

public interface TelefoneService extends Repository<Telefone> {

    List<Telefone> listAllTelefonesDoContato(Contato contato);

}
