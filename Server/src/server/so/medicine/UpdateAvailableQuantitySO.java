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
import server.validation.ValidationException;
import server.validation.Validator;

/**
 *
 * @author ANA
 */
public class UpdateAvailableQuantitySO extends AbstractSO{
     private final RepositoryMedicine repositoryMedicine;

    public UpdateAvailableQuantitySO() {
        this.repositoryMedicine = new RepositoryMedicine();
    }

    @Override
    protected void precondition(Object param) throws Exception {
        if (param == null || !(param instanceof Medicine)) {
            throw new Exception("Proslijeđeni parametar nije validan. Potrebno je proslijediti objekat klase Medicine.");
        }
        Medicine medicine = (Medicine) param;
        try {
            Validator.startValidation()
                    .validateNotNullOrEmpty(medicine.getName(), "Lijek mora imati naziv.")
                    .validateQuantity(medicine.getAvailableQuantity(), "Dostupna količina ne može biti manja od 0.")
                    .validatePrice(medicine.getPrice(), "Cijena ne može biti manja od 0.").throwIfInvalide();
        } catch (ValidationException e) {
            throw e;
        }
    }

    @Override
    protected void executeTransaction(Object param) throws Exception {
        repositoryMedicine.updateAvailableQuantity((Medicine) param);
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
