package br.com.arthurpereira.contacts.service;

import br.com.arthurpereira.contacts.entity.Contato;
import br.com.arthurpereira.contacts.entity.Usuario;
import br.com.arthurpereira.contacts.repository.Repository;

import java.util.List;

public interface ContatoService extends Repository<Contato> {

    List<Contato> listAllContatosDoUsuario(Usuario usuario);

    List<Contato> buscarContatosDoUsuarioPeloNome(String param, Usuario usuario);

}
