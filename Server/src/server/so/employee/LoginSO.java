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
public class LoginSO extends AbstractSO {

    private final RepositoryEmployee repositoryEmployee;

    public LoginSO() {
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
                    .validateNotNullOrEmpty(employee.getPassword(), "Šifra je obavezna.").throwIfInvalide();
        } catch (ValidationException e) {
            throw e;
        }
    }

    @Override
    protected void executeTransaction(Object param) throws Exception {
        List<Employee> employees = repositoryEmployee.getAll();
        boolean signal = false;
        for (Employee employee : employees) {
            if (employee.getUsername().equals(((Employee) param).getUsername()) && employee.getPassword().equals(((Employee) param).getPassword())) {
                ((Employee) param).setFirstName(employee.getFirstName());
                ((Employee) param).setLastName(employee.getLastName());
                ((Employee) param).setJMBG(employee.getJMBG());
                ((Employee) param).setId(employee.getId());
                signal = true;
                break;
            }
        }
        if (!signal) {
            throw new Exception("Unknown user!");
        }
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
