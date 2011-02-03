package br.org.servicos;

import br.org.dao.AtributoDAO;
import br.org.dao.DocumentoDAO;
import br.org.fdte.persistence.Atributo;
import br.org.fdte.persistence.TemplateDocumento;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class DocumentoServico {

    EntityManager manager;

    public void save(TemplateDocumento doc) {

    	this.manager = DBManager.openManager();
        DocumentoDAO docDao = new DocumentoDAO(manager);
        AtributoDAO attDao = new AtributoDAO(manager);
        Collection<Atributo> listAtributos = doc.getAtributoCollection();
        
        EntityTransaction et = this.manager.getTransaction();


        try {
            et.begin();
            docDao.save(doc);

            for (Atributo att : listAtributos) {
                    attDao.save(att);
            }

            et.commit();
        }
        //o documento já existe
        catch(EntityExistsException ex) {

            //remover todos os atributos vinculados a doc
            for (Atributo att : listAtributos) {
                    attDao.delete(att);
            }

            //salvar documento
            docDao.update(doc);

            //salvar os atributos do documento apos a criacao deste
            for (Atributo att : listAtributos) {
                    attDao.save(att);
            }
            et.commit();

        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
            et.rollback();
        }



    }


    /*public void save(TemplateDocumento doc) {

        DocumentoDAO docDao = new DocumentoDAO(manager);


        try {
            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();

            
            docDao.save(doc);

            this.manager.getTransaction().commit();

        } catch (EntityExistsException excEntityExists) {
            this.manager.getTransaction().rollback();
            try {
                docDao.update(doc);
                this.manager.getTransaction().commit();
            } catch (Exception excpt) {
                this.manager.getTransaction().rollback();
            }
        }
    }*/

    public void update(TemplateDocumento doc) {

        try {

            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();


            DocumentoDAO docDao = new DocumentoDAO(manager);
            docDao.update(doc);

            this.manager.getTransaction().commit();
        } catch (Exception excpt) {
            this.manager.getTransaction().rollback();
        }


    }

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

    public void delete(TemplateDocumento doc) {

        try {

            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();

            DocumentoDAO docDao = new DocumentoDAO(manager);
            AtributoDAO atributoDao = new AtributoDAO(manager);

            //TemplateDocumento doc = docDao.getByName(doc.getNome());

            for (Atributo att : doc.getAtributoCollection()) {
                atributoDao.delete(att);
            }

            docDao.delete(doc);

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
