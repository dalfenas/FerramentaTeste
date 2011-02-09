package br.org.servicos;

import br.org.dao.ExecucaoTesteValidacaoDAO;
import br.org.dao.SuiteDAO;

import br.org.dao.SuiteValidacaoTesteValidacaoDAO;
import br.org.fdte.persistence.ExecucaoTesteValidacao;
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

    public boolean save(SuiteTesteValidacao suite, List<SuiteValidacaoTesteValidacao> list) {

        boolean isNewSuite = false;

        try {
            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();

            SuiteDAO suiteDao = new SuiteDAO(manager);
            SuiteValidacaoTesteValidacaoDAO suiteValTesteValDao = new SuiteValidacaoTesteValidacaoDAO(manager);

            SuiteTesteValidacao suiteObtida = suiteDao.getByName(suite.getNome());

            if (suiteObtida == null) {
                isNewSuite = true;
                suiteDao.save(suite);

                suiteObtida = suiteDao.getByName(suite.getNome());
                //criar os relacionamentos existente na lista recebida como parametro
                for (SuiteValidacaoTesteValidacao svtv : list) {
                    svtv.setSuiteTesteValidacao(suiteObtida);
                    suiteValTesteValDao.save(svtv);
                }
            } else {

                //remover os relacionamentos existentes da suite obtida com as caracterizacoes teste de validacao
                for (SuiteValidacaoTesteValidacao svtv : suiteValTesteValDao.getBySuite(suiteObtida)) {
                    suiteValTesteValDao.delete(svtv);
                }
                this.manager.flush();

                //criar os relacionamentos existente na lista recebida como parametro
                for (SuiteValidacaoTesteValidacao svtv : list) {
                    svtv.setSuiteTesteValidacao(suiteObtida);
                    suiteValTesteValDao.save(svtv);
                }

            }
            this.manager.getTransaction().commit();
        } catch (Exception excpt) {
            System.out.println(excpt.getMessage());
            this.manager.getTransaction().rollback();
        }

        return isNewSuite;
    }

    /* public void update(SuiteTesteValidacao suite) {

    try {

    this.manager = DBManager.openManager();
    this.manager.getTransaction().begin();

    SuiteDAO suiteDao = new SuiteDAO(manager);
    suiteDao.update(suite);

    this.manager.getTransaction().commit();
    } catch (Exception excpt) {
    this.manager.getTransaction().rollback();
    }


    }*/
    public void delete(String suiteName) {

        try {
            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();

            SuiteValidacaoTesteValidacaoDAO svtvDAO = new SuiteValidacaoTesteValidacaoDAO(manager);
            SuiteDAO suiteDao = new SuiteDAO(manager);
            ExecucaoTesteValidacaoDAO execucaoDao = new ExecucaoTesteValidacaoDAO(manager);

            SuiteTesteValidacao suite = suiteDao.getByName(suiteName);

            //remover os relacionamentos existentes com a CaracterizacaoTeste
            for (SuiteValidacaoTesteValidacao sv : svtvDAO.getBySuite(suite)) {
                svtvDAO.delete(sv);
            }

            //remover as execucoes da suite
            for (ExecucaoTesteValidacao exec : execucaoDao.getExecucoesTesteValidacaoBySuite(suite)) {
                execucaoDao.delete(exec);
            }

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

    public List<SuiteValidacaoTesteValidacao> getAllSuiteValTesteVal(String suiteName) {
        List<SuiteValidacaoTesteValidacao> list = null;

        try {
            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();

            SuiteDAO suiteDAO = new SuiteDAO(manager);
            SuiteValidacaoTesteValidacaoDAO svtvDao = new SuiteValidacaoTesteValidacaoDAO(manager);

            SuiteTesteValidacao suite = suiteDAO.getByName(suiteName);
            list = svtvDao.getBySuite(suite);

            this.manager.getTransaction().commit();
        } catch (Exception excpt) {
            this.manager.getTransaction().rollback();
        } finally {
            return list;
        }
    }
}
