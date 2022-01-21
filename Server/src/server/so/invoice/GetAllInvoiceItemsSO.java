/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.so.invoice;

import commonlib.domain.Invoice;
import commonlib.domain.InvoiceItem;
import java.util.List;
import server.repository.db.impl.RepositoryInvoice;
import server.so.AbstractSO;

/**
 *
 * @author ANA
 */
public class GetAllInvoiceItemsSO extends AbstractSO{
    private final RepositoryInvoice repositoryInvoice;
    long invoiceId;

    public GetAllInvoiceItemsSO(long invoiceId) {
        this.repositoryInvoice = new RepositoryInvoice();
        this.invoiceId = invoiceId;
    }

    @Override
    protected void precondition(Object param) throws Exception {
    }

    @Override
    protected void executeTransaction(Object param) throws Exception {
        List<InvoiceItem> items = repositoryInvoice.getInvoiceItems(invoiceId);
        for (InvoiceItem item : items) {
            ((List<InvoiceItem>)param).add(item);
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
