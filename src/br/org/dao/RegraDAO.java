package br.org.dao;

import br.org.fdte.persistence.Regra;
import javax.persistence.EntityManager;

public class RegraDAO {
    
    private final EntityManager manager;
    
    public RegraDAO(EntityManager manager) {
        this.manager = manager;
    }

    public void delete(Regra regra) {
        this.manager.remove(regra);
    }
    public void save(Regra regra) {
        this.manager.persist(regra);
    }

}
