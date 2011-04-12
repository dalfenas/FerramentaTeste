package br.org.fdte;
import br.org.fdte.persistence.CaracterizacaoTesteValidacao;
import br.org.servicos.CaracterizacaoTesteValidacaoServico;
import java.util.ArrayList;
import java.util.List;

public class ComboBoxCasoTeste implements ComboBoxDataSource{

    @Override
    public List<DataItem> getItemList() {

        List<DataItem> list = new ArrayList<DataItem>();

        List<Object> listTstVal;
        listTstVal = new CaracterizacaoTesteValidacaoServico().getAll();

        for (Object obj : listTstVal){
            CaracterizacaoTesteValidacao tstVal = (CaracterizacaoTesteValidacao)obj;
            list.add(new DataItem(tstVal.getId(), tstVal.getNome()));
        }

        return list;
    }
}
