package br.org.servicos;

import br.org.dao.CaracterizacaoTesteValidacaoDAO;
import br.org.dao.DocumentoDAO;
import br.org.dao.EspecificoDAO;
import br.org.fdte.persistence.CaracterizacaoTesteValidacao;
import br.org.fdte.persistence.Especificos;
import br.org.fdte.persistence.TemplateDocumento;
import java.util.List;
import javax.persistence.EntityManager;

public class CaracterizacaoTesteValidacaoServico {

    EntityManager manager;

    public boolean save(CaracterizacaoTesteValidacao caracterizacao) {

        boolean isNewCaracterizacao = false;

        try {

            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();

            CaracterizacaoTesteValidacaoDAO caractDao = new CaracterizacaoTesteValidacaoDAO(manager);
            DocumentoDAO documentoDao = new DocumentoDAO(manager);
            EspecificoDAO especificoDao = new EspecificoDAO(manager);

            caracterizacao.setDocumentoEntrada(documentoDao.getByName(caracterizacao.getDocumentoEntrada().getNome()));
            caracterizacao.setDocumentoSaidaNegativa(documentoDao.getByName(caracterizacao.getDocumentoSaidaNegativa().getNome()));
            caracterizacao.setDocumentoSaidaPositiva(documentoDao.getByName(caracterizacao.getDocumentoSaidaPositiva().getNome()));

            CaracterizacaoTesteValidacao caractObtida = caractDao.getByName(caracterizacao.getNome());

            if (caractObtida == null) {
                isNewCaracterizacao = true;
                caractDao.save(caracterizacao);
            } else {
                caractObtida.setCasosNegativos(caracterizacao.getCasosNegativos());
                caractObtida.setCasosPositivos(caracterizacao.getCasosPositivos());
                caractObtida.setClasseValidacaoSaidaNegativa(caracterizacao.getClasseValidacaoSaidaNegativa());
                caractObtida.setClasseValidacaoSaidaPositiva(caracterizacao.getClasseValidacaoSaidaPositiva());
                caractObtida.setClasseValidacaoSaidaNegativa(caracterizacao.getClasseValidacaoSaidaNegativa());
                caractObtida.setComentario(caracterizacao.getComentario());
                caractObtida.setNome(caracterizacao.getNome());

                //remover especificos                
                for (Especificos especifico : caractObtida.getEspecificosCollection()) {
                    especificoDao.delete(especifico);
                }

                for (Especificos especifico : caracterizacao.getEspecificosCollection()) {
                    especifico.setIdCaracterizacaoTesteValidacao(caractObtida);
                    especificoDao.save(especifico);
                }

                caractObtida.setEspecificosCollection(caracterizacao.getEspecificosCollection());
                caractDao.update(caractObtida);

            }

            this.manager.getTransaction().commit();
        } catch (Exception excpt) {
            System.out.println(excpt.getMessage());
            this.manager.getTransaction().rollback();
        }

        return isNewCaracterizacao;
    }

    public void update(CaracterizacaoTesteValidacao caracterizacao) {
        try {

            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();


            CaracterizacaoTesteValidacaoDAO dao = new CaracterizacaoTesteValidacaoDAO(manager);
            dao.update(caracterizacao);

            this.manager.getTransaction().commit();
        } catch (Exception excpt) {
            this.manager.getTransaction().rollback();
        }
    }

    public void delete(String nomeCaracterizacao) {

       try {

            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();

            CaracterizacaoTesteValidacaoDAO ctDao = new CaracterizacaoTesteValidacaoDAO(manager);
            CaracterizacaoTesteValidacao caract = ctDao.getByName(nomeCaracterizacao);

            EspecificoDAO especificoDao = new EspecificoDAO(manager);
            for (Especificos espec : caract.getEspecificosCollection()) {
                especificoDao.delete(espec);
            }

            ctDao.delete(caract);


            this.manager.getTransaction().commit();
        } catch (Exception excpt) {
            System.out.println(excpt.getMessage());
            this.manager.getTransaction().rollback();
        }


    }

    public CaracterizacaoTesteValidacao getByName(String nomeCaracterizacao) {

        CaracterizacaoTesteValidacao caracterizacao = null;

        try {
            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();

            CaracterizacaoTesteValidacaoDAO dao = new CaracterizacaoTesteValidacaoDAO(manager);

            caracterizacao = dao.getByName(nomeCaracterizacao);

            this.manager.getTransaction().commit();
        } catch (Exception excpt) {
            this.manager.getTransaction().rollback();
        } finally {
            return caracterizacao;
        }

    }

    public List<CaracterizacaoTesteValidacao> getAll() {

        List<CaracterizacaoTesteValidacao> lstCaract = null;

        try {
            this.manager = DBManager.openManager();
            this.manager.getTransaction().begin();

            CaracterizacaoTesteValidacaoDAO dao = new CaracterizacaoTesteValidacaoDAO(manager);

            lstCaract = dao.getAll();

            this.manager.getTransaction().commit();
        } catch (Exception excpt) {
            this.manager.getTransaction().rollback();
        } finally {
            return lstCaract;
        }
    }
}
