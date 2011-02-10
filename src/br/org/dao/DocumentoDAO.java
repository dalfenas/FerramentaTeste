package br.org.dao;

import br.org.fdte.persistence.TemplateDocumento;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class DocumentoDAO {

    private final EntityManager manager;

    public DocumentoDAO(EntityManager manager) {
        this.manager = manager;
    }

    public void save(TemplateDocumento doc) {
            this.manager.persist(doc);        
    }

    public void update(TemplateDocumento doc) {
        this.manager.merge(doc);
    }

    public void delete(TemplateDocumento doc) {
        this.manager.remove(doc);
    }

    public TemplateDocumento getByName(String nomeDoc) {

        Query q = this.manager.createNamedQuery("TemplateDocumento.findByNome");
        q.setParameter("nome", nomeDoc);

        if (q.getResultList().isEmpty()) {
            return null;
        }

        return (TemplateDocumento) q.getResultList().get(0);
    }

    public List<TemplateDocumento> getAll() {
        Query q = this.manager.createNamedQuery("TemplateDocumento.findAll");
        return q.getResultList();
    }
}
