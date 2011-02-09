package br.org.dao;

import br.org.fdte.persistence.CaracterizacaoTesteValidacao;
import br.org.fdte.persistence.SuiteTesteValidacao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class SuiteDAO {

    private final EntityManager manager;

    public SuiteDAO(EntityManager manager) {
        this.manager = manager;
    }

    public SuiteTesteValidacao getByName(String nomeSuite) {

        Query q = this.manager.createNamedQuery("SuiteTesteValidacao.findByNome");
        q.setParameter("nome", nomeSuite);

        if (q.getResultList().isEmpty()) {
            return null;
        }

        return (SuiteTesteValidacao) q.getResultList().get(0);
    }

    public void save(SuiteTesteValidacao suite) {
        this.manager.persist(suite);
    }

    public void update(SuiteTesteValidacao suite) {
        this.manager.merge(suite);
    }

    public void delete(SuiteTesteValidacao suite) {
        this.manager.remove(suite);
    }

    public List<SuiteTesteValidacao> getAll() {
        Query q = this.manager.createNamedQuery("SuiteTesteValidacao.findAll");
        return q.getResultList();
    }
}
