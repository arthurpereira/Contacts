package br.com.arthurpereira.contacts.repository;

import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

public interface Repository<T> {

    void save(T entity);

    T update(T entity);

    void delete(T entity);

    T find(Object id) throws EntityNotFoundException;

    List<T> list(Class<T> tipo, String campo);

    List<T> list(String sql);

    List<T> list(String sql, Object... valores);

    List<T> listAll();

    TypedQuery<T> createQuery(String sql);

    TypedQuery<T> createQuery(String sql, Object... valores);

    TypedQuery<T> createQuery(String sql, Map<String, Object> params);

    EntityManager getEntityManager();

    Session getHibernateSession();

}
