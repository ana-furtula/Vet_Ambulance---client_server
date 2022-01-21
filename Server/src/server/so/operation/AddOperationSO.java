/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.so.operation;

import commonlib.domain.Operation;
import server.repository.db.DbRepository;
import server.repository.db.impl.Repository;
import server.repository.db.impl.RepositoryOperation;
import server.so.AbstractSO;
import server.validation.ValidationException;
import server.validation.Validator;

/**
 *
 * @author ANA
 */
public class AddOperationSO extends AbstractSO {

    private final DbRepository repositoryOperation;

    public AddOperationSO() {
        this.repositoryOperation = new Repository();
    }

    @Override
    protected void precondition(Object param) throws Exception {
        if (param == null || !(param instanceof Operation)) {
            throw new Exception("Proslijeđeni parametar nije validan. Potrebno je proslijediti objekat klase Operation.");
        }
        Operation operation = (Operation) param;
        try {
            Validator.startValidation()
                    .validateNotNullOrEmpty(operation.getName(), "Operacija mora imati naziv.")
                    .validatePrice(operation.getPrice(), "Cijena ne može biti manja od 0.").throwIfInvalide();
        } catch (ValidationException e) {
            throw e;
        }
    }

    @Override
    protected void executeTransaction(Object param) throws Exception {
        repositoryOperation.add((Operation) param);
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
