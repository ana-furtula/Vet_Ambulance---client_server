/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.so.medicine;

import commonlib.domain.Medicine;
import server.repository.db.DbRepository;
import server.repository.db.impl.RepositoryMedicine;
import server.so.AbstractSO;

/**
 *
 * @author ANA
 */
public class DeleteMedicineSO extends AbstractSO{
    private final DbRepository repositoryMedicine;

    public DeleteMedicineSO() {
        this.repositoryMedicine = new RepositoryMedicine();
    }
    
    @Override
    protected void precondition(Object param) throws Exception {
        if(param==null || !(param instanceof Medicine)){
            throw new Exception("Proslijeđeni parametar nije validan. Potrebno je proslijediti objekat klase Medicine.");
        }
    }

    @Override
    protected void executeTransaction(Object param) throws Exception {
        repositoryMedicine.delete((Medicine)param);
    }
    
    @Override
    protected void commitTransaction() throws Exception{
        repositoryMedicine.commit();
    }

    @Override
    protected void rollbackTransaction() throws Exception {
        repositoryMedicine.rollback();
    }
    
}
