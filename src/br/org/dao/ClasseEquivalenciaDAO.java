package br.org.dao;

import br.org.fdte.persistence.ClasseEquivalencia;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ClasseEquivalenciaDAO {

    private final EntityManager manager;

    public ClasseEquivalenciaDAO(EntityManager manager) {
        this.manager = manager;
    }

    public List<ClasseEquivalencia> getAll() {

        List<ClasseEquivalencia> lstCE = null;

        Query q = this.manager.createNamedQuery("ClasseEquivalencia.findAll");

        lstCE = q.getResultList();
        Collections.sort(lstCE);

        return lstCE;
    }

    public void save(ClasseEquivalencia ce) {
        this.manager.persist(ce);
    }

    public ClasseEquivalencia getByName(String nomeCE) {

        Query q = this.manager.createNamedQuery("ClasseEquivalencia.findByNome");
        q.setParameter("nome", nomeCE);

        return (ClasseEquivalencia) q.getResultList().get(0);
    }

    public ClasseEquivalencia getById (int id) {
        Query q = this.manager.createNamedQuery("ClasseEquivalencia.findById");
        q.setParameter("id", id);

       return (ClasseEquivalencia) q.getResultList().get(0);

    }

    public void update(ClasseEquivalencia ce) {
        this.manager.merge(ce);
    }

    public void delete(ClasseEquivalencia ce) {
        this.manager.remove(ce);
    }
}
