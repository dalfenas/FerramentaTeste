package br.org.fdte;

import br.org.fdte.dao.ClasseEquivalenciaDAO;
import br.org.fdte.persistence.ClasseEquivalencia;
import java.util.ArrayList;
import java.util.List;

public class ComboBoxClasseEquivalencia implements ComboBoxDataSource {

    public List<DataItem> getItemList() {

        List<DataItem> list = new ArrayList<DataItem>();

        List<ClasseEquivalencia> listCE;
        listCE = ClasseEquivalenciaDAO.getAll();

        for (ClasseEquivalencia cle : listCE)
            list.add(new DataItem(cle.getId(), cle.getNome()));

        return list;
    }
}
