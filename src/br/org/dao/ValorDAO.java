package br.org.dao;

import br.org.fdte.persistence.Valor;
import javax.persistence.EntityManager;

public class ValorDAO {

    private final EntityManager manager;

    public ValorDAO(EntityManager manager) {
        this.manager = manager;
    }

    public void delete(Valor valor) {
        this.manager.remove(valor);
    }

    public void save(Valor valor) {
        this.manager.persist(valor);
    }

    public void update(Valor valor) {
        this.manager.merge(valor);
    }

}
