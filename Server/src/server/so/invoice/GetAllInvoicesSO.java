/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.so.invoice;

import commonlib.domain.Invoice;
import commonlib.domain.InvoiceItem;
import java.util.LinkedList;
import java.util.List;
import server.repository.db.DbRepository;
import server.repository.db.impl.RepositoryInvoice;
import server.so.AbstractSO;
import server.so.medicine.UpdateAvailableQuantitySO;
import server.validation.ValidationException;
import server.validation.Validator;

/**
 *
 * @author ANA
 */
public class GetAllInvoicesSO extends AbstractSO {

    private final RepositoryInvoice repositoryInvoice;

    public GetAllInvoicesSO() {
        this.repositoryInvoice = new RepositoryInvoice();
    }

    @Override
    protected void precondition(Object param) throws Exception {
    }

    @Override
    protected void executeTransaction(Object param) throws Exception {
        List<Invoice> invoices = repositoryInvoice.getAll();
        for (Invoice invoice : invoices) {
            List<InvoiceItem> invoiceItems = repositoryInvoice.getInvoiceItems(invoice.getId());
            invoice.setItems(invoiceItems);
        }
        for (Invoice invoice : invoices) {
            ((List<Invoice>)param).add(invoice);
        }
    }

    @Override
    protected void commitTransaction() throws Exception {
        repositoryInvoice.commit();
    }

    @Override
    protected void rollbackTransaction() throws Exception {
        repositoryInvoice.rollback();
    }

}
