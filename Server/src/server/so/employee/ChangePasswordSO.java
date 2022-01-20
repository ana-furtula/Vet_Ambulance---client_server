/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.so.employee;

import commonlib.domain.Employee;
import java.util.List;
import server.repository.db.impl.RepositoryEmployee;
import server.so.AbstractSO;
import server.validation.ValidationException;
import server.validation.Validator;

/**
 *
 * @author ANA
 */
public class ChangePasswordSO extends AbstractSO{
    private final RepositoryEmployee repositoryEmployee;

    public ChangePasswordSO() {
        this.repositoryEmployee = new RepositoryEmployee();
    }

    @Override
    protected void precondition(Object param) throws Exception {
        if (param == null || !(param instanceof Employee)) {
            throw new Exception("Proslijeđeni parametar nije validan. Potrebno je proslijediti objekat klase Employee.");
        }
        Employee employee = (Employee) param;
        try {
            Validator.startValidation()
                    .validateNotNullOrEmpty(employee.getUsername(), "Korisničko ime je obavezno.")
                    .validateNotNullOrEmpty(employee.getPassword(), "Šifra je obavezna.")
                    .validateNotNullOrEmpty(employee.getFirstName(), "Ime zaposlenog je obavezno.")
                    .validateNotNullOrEmpty(employee.getLastName(), "Prezime zaposlenog je obavezno")
                    .validateNotNullOrEmpty(employee.getJMBG(), "JMBG zaposlenog je obavezan")
                    .throwIfInvalide();
        } catch (ValidationException e) {
            throw e;
        }
    }

    @Override
    protected void executeTransaction(Object param) throws Exception {
        repositoryEmployee.changePassword((Employee)param);
    }

    @Override
    protected void commitTransaction() throws Exception {
        repositoryEmployee.commit();
    }

    @Override
    protected void rollbackTransaction() throws Exception {
        repositoryEmployee.rollback();
    }
}
