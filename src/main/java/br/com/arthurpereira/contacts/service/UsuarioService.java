package br.com.arthurpereira.contacts.service;

import br.com.arthurpereira.contacts.entity.Usuario;
import br.com.arthurpereira.contacts.repository.Repository;

public interface UsuarioService extends Repository<Usuario> {

    Usuario findByFacebookId(Long id);

}
