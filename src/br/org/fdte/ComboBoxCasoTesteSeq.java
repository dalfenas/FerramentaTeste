package br.org.fdte;

import br.org.fdte.dao.CaracterizacaoTstSequenciaDAO;
import br.org.fdte.persistence.CaracterizacaoTesteSequencia;
import java.util.ArrayList;
import java.util.List;


public class ComboBoxCasoTesteSeq implements ComboBoxDataSource{
     public List<DataItem> getItemList() {         
        List<DataItem> list = new ArrayList<DataItem>();
        List<CaracterizacaoTesteSequencia> listTstSeq;
        listTstSeq = CaracterizacaoTstSequenciaDAO.getAll();
        for(CaracterizacaoTesteSequencia tstSeq : listTstSeq)
           list.add(new DataItem(tstSeq.getId(), tstSeq.getNome()));
        return list;
     }

}
