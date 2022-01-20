/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.so.invoice;

import commonlib.domain.Invoice;
import commonlib.domain.InvoiceItem;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import server.components.InvoiceDate;
import server.repository.db.impl.RepositoryInvoice;
import server.so.AbstractSO;
import server.validation.ValidationException;
import server.validation.Validator;

/**
 *
 * @author ANA
 */
public class FindInvoicesByDate extends AbstractSO {

    private final RepositoryInvoice repositoryInvoice;

    public FindInvoicesByDate() {
        this.repositoryInvoice = new RepositoryInvoice();
    }

    @Override
    protected void precondition(Object param) throws Exception {
        if (param == null || !(param instanceof InvoiceDate)) {
            throw new Exception("Proslijeđeni parametar nije validan. Potrebno je proslijediti objekat klase InvoiceDate.");
        }
        InvoiceDate invoiceDate = (InvoiceDate) param;
//        try {
//            
//        } catch (ValidationException e) {
//            throw e;
//        }
    }

    @Override
    protected void executeTransaction(Object param) throws Exception {
        AbstractSO getAllInvoices = new GetAllInvoicesSO();
        List<Invoice> invoices = new LinkedList<>();
        getAllInvoices.execute(invoices);
        invoices.removeIf(i -> !(i.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).startsWith(((InvoiceDate)param).getDate())));
        for (Invoice invoice : invoices) {
            ((InvoiceDate) param).getInvoices().add(invoice);
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
