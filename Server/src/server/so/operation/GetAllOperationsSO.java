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
public class GetAllOperationsSO extends AbstractSO {

    private final DbRepository repositoryOperation;

    public GetAllOperationsSO() {
        this.repositoryOperation = new RepositoryOperation();
    }

    @Override
    protected void precondition(Object param) throws Exception {
    }

    @Override
    protected void executeTransaction(Object param) throws Exception {
        List<Operation> operations = repositoryOperation.getAll();
        for (Operation operation : operations) {
            ((List<Operation>) param).add(operation);
        }
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
