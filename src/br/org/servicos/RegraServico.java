package br.org.servicos;

import br.org.dao.DocumentoDAO;
import br.org.dao.RegraDAO;
import br.org.fdte.persistence.Regra;
import br.org.fdte.persistence.TemplateDocumento;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.swing.text.Document;

public class RegraServico {
    
    EntityManager manager;

    public void deleteAllByDoc(String docName) {

        try {

            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();

            DocumentoDAO documentoDAO = new DocumentoDAO(manager);

            TemplateDocumento doc = documentoDAO.getByName(docName);
            Collection<Regra> listaRegras = doc.getRegraCollection();

            RegraDAO regraDao = new RegraDAO(manager);
            for (Regra regra : listaRegras)
                    regraDao.delete(regra);

            this.manager.getTransaction().commit();

        } catch (Exception excpt) {
            this.manager.getTransaction().rollback();
        }


    }

    public void saveAllByDoc(String docName) {

        try {

            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();

            DocumentoDAO documentoDAO = new DocumentoDAO(manager);

            TemplateDocumento doc = documentoDAO.getByName(docName);
            Collection<Regra> listaRegras = doc.getRegraCollection();

            RegraDAO regraDao = new RegraDAO(manager);
            for (Regra regra : listaRegras)
                    regraDao.save(regra);

            this.manager.getTransaction().commit();

        } catch (Exception excpt) {
            this.manager.getTransaction().rollback();
        }


    }

}
