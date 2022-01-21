/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.so.medicine;

import commonlib.domain.Invoice;
import commonlib.domain.InvoiceItem;
import commonlib.domain.Medicine;
import java.math.BigDecimal;
import server.repository.db.impl.RepositoryInvoice;
import server.repository.db.impl.RepositoryMedicine;
import server.so.AbstractSO;
import server.validation.ValidationException;
import server.validation.Validator;

/**
 *
 * @author ANA
 */
public class CheckAvailableQuantitySO extends AbstractSO {

    private final RepositoryMedicine repositoryMedicine;
    private final RepositoryInvoice repositoryInvoice;

    public CheckAvailableQuantitySO() {
        this.repositoryMedicine = new RepositoryMedicine();
        this.repositoryInvoice = new RepositoryInvoice();
    }

    @Override
    protected void precondition(Object param) throws Exception {
        if (param == null || !(param instanceof InvoiceItem)) {
            throw new Exception("Proslijeđeni parametar nije validan. Potrebno je proslijediti objekat klase InvoiceItem.");
        }
    }

    @Override
    protected void executeTransaction(Object param) throws Exception {
        InvoiceItem item = (InvoiceItem) param;
        if (item.getMedicine() != null) {
            BigDecimal oldQuantity = null;
            try {
                oldQuantity = repositoryInvoice.getQuantity(item);
            } catch (Exception ex) {
            }
            BigDecimal availableQuantity = repositoryMedicine.getAvailableQuantity(item.getMedicine().getId());
            if (oldQuantity != null) {
                if (oldQuantity.compareTo(item.getQuantity()) < 0) {
                    BigDecimal change = item.getQuantity().subtract(oldQuantity);
                    if (availableQuantity.compareTo(change) < 0) {
                        throw new Exception("Nema dovoljno količine lijeka " + item.getMedicine().getName() + " na zalihama.");
                    }
                }
            } else {
                if (availableQuantity.compareTo(item.getQuantity()) < 0) {
                    throw new Exception("Nema dovoljno količine lijeka " + item.getMedicine().getName() + " na zalihama.");
                }
            }
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
