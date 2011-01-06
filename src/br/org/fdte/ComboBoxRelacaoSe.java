package br.org.fdte;

import br.org.fdte.dao.RelacaoSeDAO;
import java.util.List;
import br.org.fdte.persistence.RelacaoSe;
import java.util.ArrayList;

public class ComboBoxRelacaoSe implements ComboBoxDataSource {

     public List<DataItem> getItemList() {

        List<DataItem> list = new ArrayList<DataItem>();

        List<RelacaoSe> listRelacao;
        listRelacao = RelacaoSeDAO.getAll();

        for (RelacaoSe rel : listRelacao)
            list.add(new DataItem(rel.getId(), rel.getNome()));

        return list;
     }

}
