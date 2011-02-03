package br.org.dao;

import br.org.fdte.persistence.Atributo;
import javax.persistence.EntityManager;

public class AtributoDAO {

     private final EntityManager manager;

    public AtributoDAO(EntityManager manager) {
        this.manager = manager;
    }

    public void delete(Atributo att) {
        this.manager.remove(att);
    }

    public void save(Atributo att) {
        this.manager.persist(att);
    }

    public void update(Atributo att) {
        this.manager.persist(att);
    }

}
