package br.org.dao;

import br.org.fdte.persistence.ExecucaoTesteValidacao;
import br.org.fdte.persistence.SuiteTesteValidacao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ExecucaoTesteValidacaoDAO {

    private final EntityManager manager;

    public ExecucaoTesteValidacaoDAO(EntityManager manager) {
        this.manager = manager;
    }
    
    public List<ExecucaoTesteValidacao> getExecucoesTesteValidacaoBySuite (SuiteTesteValidacao suiteId) {

        Query q = this.manager.createNamedQuery("ExecucaoTesteValidacao.findBySuite");
        q.setParameter("idSuite", suiteId);
        return q.getResultList();
    }

    public ExecucaoTesteValidacao getExecucaoTesteValidacao(int id) {

        Query q = this.manager.createNamedQuery("ExecucaoTesteValidacao.findById");
        q.setParameter("id", id);

        return (ExecucaoTesteValidacao)q.getResultList().get(0);
    }
}
