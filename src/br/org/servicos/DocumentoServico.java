package br.org.servicos;

import br.org.dao.AtributoDAO;
import br.org.dao.DocumentoDAO;
import br.org.fdte.persistence.Atributo;
import br.org.fdte.persistence.TemplateDocumento;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class DocumentoServico {

    EntityManager manager;


    public boolean save(TemplateDocumento doc) {

        boolean isNewDocument = false;

        this.manager = DBManager.openManager();

        DocumentoDAO docDao = new DocumentoDAO(manager);
        AtributoDAO attDao = new AtributoDAO(manager);

        EntityTransaction et = this.manager.getTransaction();
        try {
            et.begin();

            TemplateDocumento docObtido = docDao.getByName(doc.getNome());

            if (docObtido == null) {
                docDao.save(doc);
                isNewDocument = true;
            } else {

                docObtido.setArquivoXsd(doc.getArquivoXsd());
                docObtido.setDirecao(doc.getDirecao());
                docObtido.setRegraCollection(doc.getRegraCollection());
                docObtido.setTipoFisico(doc.getTipoFisico());

                for (Atributo att : docObtido.getAtributoCollection()) {
                    attDao.delete(att);
                }

                for (Atributo att : doc.getAtributoCollection()) {
                    att.setIdTemplateDocumento(docObtido);
                    attDao.save(att);
                }

                docObtido.setAtributoCollection(doc.getAtributoCollection());
               
            }

            et.commit();
        } catch (Exception ex) {
            et.rollback();
            System.out.println(ex.getMessage());
        }
        return isNewDocument;
    }

    /*public void save(TemplateDocumento doc) {

        this.manager = DBManager.openManager();

        DocumentoDAO docDao = new DocumentoDAO(manager);
        AtributoDAO attDao = new AtributoDAO(manager);

        EntityTransaction et = this.manager.getTransaction();
        try {
            et.begin();

            //se o documento nao existe
            if (doc.getId() == null)
                docDao.save(doc);
            else {

                //for (Atributo att : doc.getAtributoCollection()) {
                   // attDao.delete(att);
                //}
                attDao.deleteAll(doc);

                for (Atributo att : doc.getAtributoCollection()) {
                    att.setIdTemplateDocumento(doc);
                    attDao.save(att);
                }

                doc.setAtributoCollection(doc.getAtributoCollection());
                docDao.update(doc);

            }

            et.commit();
        } catch (Exception ex) {
            et.rollback();
            System.out.println(ex.getMessage());
        }
    }
*/

    /*public void update(TemplateDocumento doc) {

    try {

    this.manager = DBManager.openManager();
    this.manager.getTransaction().begin();


    DocumentoDAO docDao = new DocumentoDAO(manager);
    docDao.update(doc);

    this.manager.getTransaction().commit();
    } catch (Exception excpt) {
    this.manager.getTransaction().rollback();
    }


    }*/
    public void delete(String docName) {

        try {

            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();
            DocumentoDAO docDao = new DocumentoDAO(manager);

            AtributoDAO atributoDao = new AtributoDAO(manager);

            for (Atributo att : docDao.getByName(docName).getAtributoCollection()) {
                atributoDao.delete(att);
            }

            docDao.delete(docDao.getByName(docName));

            this.manager.getTransaction().commit();
        } catch (Exception excpt) {
            this.manager.getTransaction().rollback();
        }
    }

   public TemplateDocumento getByName(String nome) {

        TemplateDocumento doc = null;

        try {
            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();

            DocumentoDAO docDao = new DocumentoDAO(manager);

            doc = docDao.getByName(nome);

            this.manager.getTransaction().commit();
        } catch (Exception excpt) {
            System.out.println(excpt.getMessage());
            this.manager.getTransaction().rollback();
        } finally {
            return doc;
        }

    }

    public List<TemplateDocumento> getAll() {

        List<TemplateDocumento> lstDoc = null;

        try {
            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();

            DocumentoDAO docDao = new DocumentoDAO(manager);

            lstDoc = docDao.getAll();

            this.manager.getTransaction().commit();
        } catch (Exception excpt) {
            this.manager.getTransaction().rollback();
        } finally {
            return lstDoc;
        }
    }
}
