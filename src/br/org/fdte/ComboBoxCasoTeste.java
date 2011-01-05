package br.org.fdte;

import br.org.fdte.dao.CaracterizacaoTstValidacaoDAO;
import br.org.fdte.persistence.CaracterizacaoTesteValidacao;
import java.util.ArrayList;
import java.util.List;

public class ComboBoxCasoTeste implements ComboBoxDataSource{

    public List<DataItem> getItemList() {

        List<DataItem> list = new ArrayList<DataItem>();

        List<CaracterizacaoTesteValidacao> listTstVal;
        listTstVal = CaracterizacaoTstValidacaoDAO.getAll();        

        for (CaracterizacaoTesteValidacao tstVal : listTstVal)
            list.add(new DataItem(tstVal.getId(), tstVal.getNome()));

        return list;
    }
}