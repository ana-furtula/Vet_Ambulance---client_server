/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.so;

/**
 *
 * @author ANA
 */
public abstract class AbstractSO {

    public void execute(Object param) throws Exception {
        try {
            precondition(param);
            startTransaction();
            executeTransaction(param);
            commitTransaction();
            System.out.println("Uspesno izvrsena operacija");
        } catch (Exception ex) {
            rollbackTransaction();
            System.out.println("Neuspesno izvrsena operacija");
            throw ex;
        }
    }

    protected abstract void precondition(Object param) throws Exception;

    protected void startTransaction() throws Exception {
    }

    protected void commitTransaction() throws Exception {
    }

    protected void rollbackTransaction() throws Exception {
    }

    protected abstract void executeTransaction(Object param) throws Exception;

}
