package br.org.fdte;

import br.org.fdte.dao.RelacaoEntaoDAO;
import java.util.List;
import br.org.fdte.persistence.RelacaoEntao;
import java.util.ArrayList;

public class ComboBoxRelacaoEntao implements ComboBoxDataSource {

     public List<DataItem> getItemList() {

        List<DataItem> list = new ArrayList<DataItem>();

        List<RelacaoEntao> listRelacao;
        listRelacao = RelacaoEntaoDAO.getAll();

        for (RelacaoEntao rel : listRelacao)
            list.add(new DataItem(rel.getId(), rel.getNome()));

        return list;
     }

}