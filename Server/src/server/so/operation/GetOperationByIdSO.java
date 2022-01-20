/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.so.operation;

import commonlib.domain.Operation;
import java.util.List;
import server.repository.db.DbRepository;
import server.repository.db.impl.RepositoryOperation;
import server.so.AbstractSO;

/**
 *
 * @author ANA
 */
public class GetOperationByIdSO extends AbstractSO {

    private final DbRepository repositoryOperation;

    public GetOperationByIdSO() {
        this.repositoryOperation = new RepositoryOperation();
    }

    @Override
    protected void precondition(Object param) throws Exception {
        if (param == null || !(param instanceof Operation)) {
            throw new Exception("Proslijeđeni parametar nije validan. Potrebno je proslijediti objekat klase Operation.");
        }
    }

    @Override
    protected void executeTransaction(Object param) throws Exception {
        Operation operation = (Operation) repositoryOperation.getById(((Operation) param).getId());
        ((Operation) param).setName(operation.getName());
        ((Operation) param).setPrice(operation.getPrice());
    }

    @Override
    protected void commitTransaction() throws Exception {
        repositoryOperation.commit();
    }

    @Override
    protected void rollbackTransaction() throws Exception {
        repositoryOperation.rollback();
    }
}
