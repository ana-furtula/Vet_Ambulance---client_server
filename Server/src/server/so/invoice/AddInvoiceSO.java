/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.so.invoice;

import commonlib.domain.Invoice;
import commonlib.domain.InvoiceItem;
import server.so.medicine.*;
import server.repository.db.DbRepository;
import server.repository.db.impl.Repository;
import server.repository.db.impl.RepositoryInvoice;
import server.so.AbstractSO;
import server.validation.ValidationException;
import server.validation.Validator;

/**
 *
 * @author ANA
 */
public class AddInvoiceSO extends AbstractSO {

    private final DbRepository repositoryInvoice;

    public AddInvoiceSO() {
        this.repositoryInvoice = new Repository();
    }

    @Override
    protected void precondition(Object param) throws Exception {
        if (param == null || !(param instanceof Invoice)) {
            throw new Exception("Proslijeđeni parametar nije validan. Potrebno je proslijediti objekat klase Invoice.");
        }
        Invoice invoice = (Invoice) param;
        try {
            Validator.startValidation()
                    .validateNotNull(invoice.getClient(), "Neophodno je unijeti klijenta.")
                    .validateNotNull(invoice.getEmployee(), "Neophodno je unijeti zaposlenog koji je izdao racun.")
                    .validatePrice(invoice.getTotalValue(), "Cijena ne može biti manja od 0.").throwIfInvalide();
            if(invoice.getItems()==null || invoice.getItems().size()==0){
                throw new Exception("Račun mora sadržati barem jednu stavku.");
            }
            checkAvailableQuantities((Invoice)param);
        } catch (ValidationException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    protected void executeTransaction(Object param) throws Exception {
        repositoryInvoice.add((Invoice)param);
        for ( InvoiceItem item : ((Invoice)param).getItems()) {
            repositoryInvoice.add(item);
        }
        updateMedicinesQuantities((Invoice) param);
    }

    @Override
    protected void commitTransaction() throws Exception {
        repositoryInvoice.commit();
    }

    @Override
    protected void rollbackTransaction() throws Exception {
        repositoryInvoice.rollback();
    }
    
    private void checkAvailableQuantities(Invoice invoice) throws Exception {
        AbstractSO checkAvailableQuantitySO = new CheckAvailableQuantitySO();
        for (InvoiceItem item : invoice.getItems()) {
            if (item.getMedicine() != null) {
                checkAvailableQuantitySO.execute(item);
            }
        }
    }

    private void updateMedicinesQuantities(Invoice invoice) throws Exception {
        AbstractSO updateMedicineQuantitySO = new UpdateAvailableQuantitySO();
        for (InvoiceItem item : invoice.getItems()) {
            if (item.getMedicine() != null) {
                updateMedicineQuantitySO.execute(item.getMedicine());
            }
        }
    }

}
