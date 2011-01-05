package br.org.fdte;


public abstract interface AtualizacaoTela {
    
    final static String entidadeClasseEquivalencia = "CLASSE_EQUIVALENCIA";
    final static String entidadeDocumento = "DOCUMENTO";
    final static String entidadeTesteValidacao = "TESTE_VALIDACAO";
    final static String entidadeSuiteValidacao = "SUITE_VALIDACAO";


    public void atualizarTela();
}
