package br.org.servicos;

import br.org.dao.ClasseEquivalenciaDAO;
import br.org.dao.ValorDAO;
import br.org.fdte.persistence.ClasseEquivalencia;
import br.org.fdte.persistence.Valor;
import java.util.List;
import javax.persistence.EntityManager;

public class ClasseEquivalenciaServico {// implements ServiceInterface {

    EntityManager manager;

    public List<ClasseEquivalencia> getAll() {

        List<ClasseEquivalencia> lstCE = null;

        try {
            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();

            ClasseEquivalenciaDAO ceDao = new ClasseEquivalenciaDAO(manager);

            lstCE = ceDao.getAll();

            this.manager.getTransaction().commit();
        } catch (Exception excpt) {
            this.manager.getTransaction().rollback();
        } finally {
            return lstCE;
        }
    }

    public ClasseEquivalencia getByName(String nomeCE) {

        ClasseEquivalencia ce = null;

        try {
            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();

            ClasseEquivalenciaDAO ceDao = new ClasseEquivalenciaDAO(manager);
            ce = ceDao.getByName(nomeCE);

            this.manager.getTransaction().commit();
        } catch (Exception excpt) {
            this.manager.getTransaction().rollback();
        } finally {
            //this.manager.close();
            //this.manager = null;
            return ce;
        }

    }

    public ClasseEquivalencia getById (int id) {

        ClasseEquivalencia ce = null;

        try {
            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();

            ClasseEquivalenciaDAO ceDao = new ClasseEquivalenciaDAO(manager);
            ce = ceDao.getById(id);

            this.manager.getTransaction().commit();
        } catch (Exception excpt) {
            this.manager.getTransaction().rollback();
        } finally {
            //this.manager.close();
            //this.manager = null;
            return ce;
        }


    }


    public void save(ClasseEquivalencia ce) {

        try {

            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();

            ClasseEquivalenciaDAO ceDao = new ClasseEquivalenciaDAO(manager);
            ceDao.save(ce);

            this.manager.getTransaction().commit();
        } catch (Exception excpt) {
            this.manager.getTransaction().rollback();
        }
    }

    public void update(ClasseEquivalencia ce) {

        try {

            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();


            ClasseEquivalenciaDAO ceDao = new ClasseEquivalenciaDAO(manager);
            ceDao.update(ce);

            this.manager.getTransaction().commit();
        } catch (Exception excpt) {
            this.manager.getTransaction().rollback();
        }


    }

    public void delete(String nomeCE) {
       try {

            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();

            ClasseEquivalenciaDAO ceDao = new ClasseEquivalenciaDAO(manager);
            ClasseEquivalencia ce = ceDao.getByName(nomeCE);

            ValorDAO valorDao = new ValorDAO(manager);
            for (Valor valor : ce.getValorCollection()) {
                valorDao.delete(valor);
            }

            ceDao.delete(ceDao.getByName(nomeCE));


            this.manager.getTransaction().commit();
        } catch (Exception excpt) {
            System.out.println(excpt.getMessage());
            this.manager.getTransaction().rollback();
        }


    }
}
