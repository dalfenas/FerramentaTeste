package br.org.servicos;

import br.org.dao.SuiteDAO;
import br.org.dao.SuiteValidacaoTesteValidacaoDAO;
import br.org.fdte.persistence.SuiteTesteValidacao;
import br.org.fdte.persistence.SuiteValidacaoTesteValidacao;
import java.util.List;
import javax.persistence.EntityManager;

public class SuiteServico {

    EntityManager manager;

    public SuiteTesteValidacao getByName(String nomeSuite) {

        SuiteTesteValidacao suite = null;

        try {
            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();

            SuiteDAO suiteDao = new SuiteDAO(manager);

            suite = suiteDao.getByName(nomeSuite);

            this.manager.getTransaction().commit();
        } catch (Exception excpt) {
            this.manager.getTransaction().rollback();
        } finally {
            return suite;
        }

    }

    public void save(SuiteTesteValidacao suite, List<SuiteValidacaoTesteValidacao> list) {

        try {
            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();

            SuiteDAO suiteDao = new SuiteDAO(manager);
            suiteDao.save(suite);

            SuiteValidacaoTesteValidacaoDAO suiteValTstVal = new SuiteValidacaoTesteValidacaoDAO(manager);
            for (SuiteValidacaoTesteValidacao itemList : list) {
                suiteValTstVal.save(itemList);
            }

            this.manager.getTransaction().commit();
        } catch (Exception excpt) {
            this.manager.getTransaction().rollback();
        }
    }

    public void update(SuiteTesteValidacao suite) {

        try {

            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();

            SuiteDAO suiteDao = new SuiteDAO(manager);
            suiteDao.update(suite);

            this.manager.getTransaction().commit();
        } catch (Exception excpt) {
            this.manager.getTransaction().rollback();
        }


    }

    public void delete(SuiteTesteValidacao suite) {

        try {
            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();

            SuiteValidacaoTesteValidacaoDAO svtvDAO = new SuiteValidacaoTesteValidacaoDAO(manager);
            List<SuiteValidacaoTesteValidacao> list = svtvDAO.getBySuite(suite);
            for (SuiteValidacaoTesteValidacao sv : list) {
                svtvDAO.delete(sv);
            }

            SuiteDAO suiteDao = new SuiteDAO(manager);
            suiteDao.delete(suite);

            this.manager.getTransaction().commit();
        } catch (Exception excpt) {
            this.manager.getTransaction().rollback();
        }
    }

    public List<SuiteTesteValidacao> getAll() {

        List<SuiteTesteValidacao> lst = null;

        try {
            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();

            SuiteDAO suiteDao = new SuiteDAO(manager);
            lst = suiteDao.getAll();

            this.manager.getTransaction().commit();
        } catch (Exception excpt) {
            this.manager.getTransaction().rollback();
        } finally {
            return lst;
        }
    }
}
