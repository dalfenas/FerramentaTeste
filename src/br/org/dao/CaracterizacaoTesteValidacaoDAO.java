package br.org.dao;

import br.org.fdte.persistence.CaracterizacaoTesteValidacao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class CaracterizacaoTesteValidacaoDAO {

    private final EntityManager manager;

    public CaracterizacaoTesteValidacaoDAO(EntityManager manager) {
        this.manager = manager;
    }

    public void save(CaracterizacaoTesteValidacao caract) {
        this.manager.persist(caract);
    }

    public void delete(CaracterizacaoTesteValidacao caract) {
        this.manager.remove(caract);
    }

    public void update(CaracterizacaoTesteValidacao caract) {
        this.manager.merge(caract);
    }

    public CaracterizacaoTesteValidacao getByName(String nome) {
       Query q = this.manager.createNamedQuery("CaracterizacaoTesteValidacao.findByNome");
       q.setParameter("nome", nome);

       return (CaracterizacaoTesteValidacao)q.getResultList().get(0);
    }

    public List<CaracterizacaoTesteValidacao> getAll() {
       Query q = this.manager.createNamedQuery("CaracterizacaoTesteValidacao.findAll");
       return q.getResultList();       
    }

}
