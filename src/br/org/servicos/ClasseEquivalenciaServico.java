package br.org.servicos;

import br.org.dao.ClasseEquivalenciaDAO;
import br.org.dao.ValorDAO;
import br.org.fdte.dao.TipoClasseEquivalenciaDAO;
import br.org.fdte.persistence.ClasseEquivalencia;
import br.org.fdte.persistence.TipoClasseEquivalencia;
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

    public ClasseEquivalencia getById(int id) {

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

    public boolean save(ClasseEquivalencia ce) {

        boolean isNewCE = false;

        try {

            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();

            ClasseEquivalenciaDAO ceDao = new ClasseEquivalenciaDAO(manager);
            ClasseEquivalencia ceObtida = ceDao.getByName(ce.getNome());

            if (ceObtida == null) {
                ceDao.save(ce);
                isNewCE = true;
            } else {

                ceObtida.setComentario(ce.getComentario());
                //ceObtida.setHeranca(ce.getHeranca());
                ceObtida.setTipo(ce.getTipo());

                ValorDAO valorDAO = new ValorDAO(manager);
                for(Valor valor : ceObtida.getValorCollection()) {
                    valorDAO.delete(valor);
                }

<<<<<<< HEAD
                for(Valor valor : ce.getValorCollection()) {
=======
                for (Valor valor : ce.getValorCollection()) {
>>>>>>> parent of deaf86f... correcao da copia de uma classe de equivalencia
                    valor.setIdClasseEquivalencia(ceObtida);
                    valorDAO.save(valor);
                }
               
            }
            this.manager.getTransaction().commit();
        } catch (Exception excpt) {
            System.out.println(excpt.getMessage());
            this.manager.getTransaction().rollback();
        }

        return isNewCE;
    }

    /* public void update(ClasseEquivalencia ce) {

    try {

    this.manager = DBManager.openManager();
    this.manager.getTransaction().begin();


    ClasseEquivalenciaDAO ceDao = new ClasseEquivalenciaDAO(manager);
    ceDao.update(ce);

    this.manager.getTransaction().commit();
    } catch (Exception excpt) {
    this.manager.getTransaction().rollback();
    }


    }*/
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
