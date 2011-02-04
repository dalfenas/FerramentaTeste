package br.org.dao;

import br.org.fdte.persistence.Atributo;
import br.org.fdte.persistence.TemplateDocumento;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

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

   /* public void deleteAll(TemplateDocumento doc) {
        Query q = this.manager.createNamedQuery("Atributo.removeAllByTemplateDocId");
        q.setParameter("idTemplateDocumento", doc);

    }*/

}
