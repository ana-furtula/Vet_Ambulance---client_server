/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.components;

import commonlib.domain.Invoice;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author ANA
 */
public class InvoiceDate {
    private List<Invoice> invoices;
    private String date;

    public InvoiceDate() {
        invoices = new LinkedList<>();
    }

    public InvoiceDate(List<Invoice> invoices, String date) {
        this.invoices = invoices;
        this.date = date;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    
}
