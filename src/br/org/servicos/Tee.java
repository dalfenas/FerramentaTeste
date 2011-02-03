package br.org.servicos;

import br.org.fdte.persistence.ClasseEquivalencia;
import java.util.List;

public class Tee {

    public static void main(String args[]) {
       // ClasseEquivalenciaServico ceService;


        List<ClasseEquivalencia> lstCE = new ClasseEquivalenciaServico().getAll();

        for (ClasseEquivalencia ce : lstCE) {
            System.out.println(ce.getNome());
        }

        ClasseEquivalencia ce = new ClasseEquivalenciaServico().getByName("ceTEMSFullName");
        System.out.println("Id : " + ce.getId() + " Nome : " + ce.getNome() );

        try {

        new ClasseEquivalenciaServico().save(ce);
        }
        catch(Exception excp) {
            new ClasseEquivalenciaServico().update(ce);
        }




    }

}
