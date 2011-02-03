package br.org.servicos;

import java.util.List;

public interface ServiceInterface {

    public List<Object> getAll();
    public Object getByName (String nome);
    public void save(Object obj);
    public void update(Object obj);
    public void delete(Object obj);

}
