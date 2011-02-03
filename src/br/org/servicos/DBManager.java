package br.org.servicos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.EntityTransaction;

public class DBManager  {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private EntityTransaction transaction;

    
    public static EntityManager openManager() {
        if (emf == null) 
            emf = Persistence.createEntityManagerFactory("TestToolPU");
        return emf.createEntityManager();
    }

    private void closeConnection() {
        emf.close();
        emf=null; 
    }
}
