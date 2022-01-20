/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.so.medicine;

import commonlib.domain.Medicine;
import java.util.List;
import server.repository.db.DbRepository;
import server.repository.db.impl.RepositoryMedicine;
import server.so.AbstractSO;
import server.validation.ValidationException;
import server.validation.Validator;

/**
 *
 * @author ANA
 */
public class GetAllMedicinesSO extends AbstractSO{
    private final DbRepository repositoryMedicine;

    public GetAllMedicinesSO() {
        this.repositoryMedicine = new RepositoryMedicine();
    }

    @Override
    protected void precondition(Object param) throws Exception {
    }

    @Override
    protected void executeTransaction(Object param) throws Exception {
        List<Medicine> medicines = repositoryMedicine.getAll();
        for (Medicine medicine : medicines) {
            ((List<Medicine>)param).add(medicine);
        }
    }

    @Override
    protected void commitTransaction() throws Exception {
        repositoryMedicine.commit();
    }

    @Override
    protected void rollbackTransaction() throws Exception {
        repositoryMedicine.rollback();
    }
}
