package br.org.dao;

import br.org.fdte.persistence.SuiteTesteValidacao;
import br.org.fdte.persistence.SuiteValidacaoTesteValidacao;
import br.org.fdte.persistence.SuiteValidacaoTesteValidacaoPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class SuiteValidacaoTesteValidacaoDAO {

    private final EntityManager manager;

    public SuiteValidacaoTesteValidacaoDAO(EntityManager manager) {
        this.manager = manager;
    }

    public void save(SuiteValidacaoTesteValidacao suiteValTstVal) {
        
       SuiteValidacaoTesteValidacaoPK suitePK = new SuiteValidacaoTesteValidacaoPK(
               suiteValTstVal.getSuiteTesteValidacao().getId(),
               suiteValTstVal.getCaracterizacaoTesteValidacao().getId());

       suiteValTstVal.setSuiteValidacaoTesteValidacaoPK(suitePK);

       this.manager.persist(suiteValTstVal);


    }

    public void delete(SuiteValidacaoTesteValidacao svtv) {
        this.manager.remove(svtv);
    }

    public List<SuiteValidacaoTesteValidacao> getBySuite(SuiteTesteValidacao stv) {

        Query q = this.manager.createNamedQuery("SuiteValidacaoTesteValidacao.findByIdSuiteValidacao");
        q.setParameter("idSuiteValidacao", stv.getId());

        return q.getResultList();

    }

}
