package br.org.fdte;

import br.org.fdte.dao.AtributoDAO;
import br.org.fdte.persistence.Atributo;
import java.util.ArrayList;
import java.util.List;

public class ComboBoxAtributo implements ComboBoxDataSource {

     public List<DataItem> getItemList() {

        List<DataItem> list = new ArrayList<DataItem>();

        List<Atributo> listAtributo;
        listAtributo = AtributoDAO.getAll();

        for (Atributo atr : listAtributo)
            list.add(new DataItem(atr.getId(), atr.getTag()));

        return list;
    }

}
