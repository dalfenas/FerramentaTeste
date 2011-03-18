package br.org.fdte;

/**
 * Sobrescreva essa classe, método event, que irá interpretar o evento duplo clique numa tabela OGrid.
 * Para ser disparada, adicione ao "OGrid" em questão
 * com o método OGrid.setDoubleClickListener.
 * @author Cabreva
 *
 */
public interface OGridDoubleClickListener {
	
	public void event(int row, int column);

}
