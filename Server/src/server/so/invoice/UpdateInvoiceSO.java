/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.so.invoice;

import commonlib.domain.Invoice;
import commonlib.domain.InvoiceItem;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import server.repository.db.DbRepository;
import server.repository.db.impl.Repository;
import server.repository.db.impl.RepositoryInvoice;
import server.repository.db.impl.RepositoryMedicine;
import server.so.AbstractSO;
import server.so.medicine.CheckAvailableQuantitySO;
import server.so.medicine.UpdateAvailableQuantitySO;
import server.validation.ValidationException;
import server.validation.Validator;

/**
 *
 * @author ANA
 */
public class UpdateInvoiceSO extends AbstractSO {

    private final DbRepository repository;
    private final RepositoryMedicine repositoryMedicine;
    private final RepositoryInvoice repositoryInvoice;

    public UpdateInvoiceSO() {
        this.repository = new Repository();
        this.repositoryMedicine = new RepositoryMedicine();
        this.repositoryInvoice = new RepositoryInvoice();
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
            if (invoice.getItems() == null || invoice.getItems().size() == 0) {
                throw new Exception("Račun mora sadržati barem jednu stavku.");
            }
            checkAvailableQuantities((Invoice) param);
        } catch (ValidationException e) {
            throw e;
        }
    }

    @Override
    protected void executeTransaction(Object param) throws Exception {
        repository.edit((Invoice) param);
        AbstractSO getAllInvoiceItems = new GetAllInvoiceItemsSO(((Invoice) param).getId());
        List<InvoiceItem> items = new LinkedList<>();
        getAllInvoiceItems.execute(items);

        Iterator<InvoiceItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            InvoiceItem item = iterator.next();
            if (!((Invoice) param).getItems().contains(item)) {
                repository.delete(item);
                iterator.remove();
            }
        }

        iterator = ((Invoice) param).getItems().iterator();
        while (iterator.hasNext()) {
            repository.edit(iterator.next());
        }

        iterator = ((Invoice) param).getItems().iterator();
        while (iterator.hasNext()) {
            InvoiceItem item = iterator.next();
            if (!items.contains(item)) {
                repository.add(item);
            }
        }

        updateMedicinesQuantities((Invoice) param);
    }

    @Override
    protected void commitTransaction() throws Exception {
        repository.commit();
    }

    @Override
    protected void rollbackTransaction() throws Exception {
        repository.rollback();
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
