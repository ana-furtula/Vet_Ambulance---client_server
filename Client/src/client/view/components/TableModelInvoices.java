/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view.components;

import commonlib.domain.Client;
import commonlib.domain.Employee;
import commonlib.domain.Invoice;
import commonlib.domain.MeasurementUnit;
import commonlib.domain.Medicine;
import java.awt.Image;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;
import sun.security.krb5.internal.tools.Klist;

/**
 *
 * @author ANA
 */
public class TableModelInvoices extends AbstractTableModel {
    
    private List<Invoice> invoices;
    private String[] columnNames = new String[]{"Datum", "Zaposleni", "Klijent", "Ukupna vrijednost", "Obrađen"};
    private Class[] columnClass = new Class[]{String.class, String.class, String.class, BigDecimal.class, ImageIcon.class};
    
    public TableModelInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }
    
    @Override
    public int getRowCount() {
        if (invoices != null) {
            return invoices.size();
        }
        return 0;
    }
    
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex > columnClass.length) {
            return Object.class;
        } else {
            return columnClass[columnIndex];
        }
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Invoice invoice = invoices.get(rowIndex);
        switch (columnIndex) {
            case 0: {
                LocalDate date = invoice.getDate();
                return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString();
            }
            case 1:
                return invoice.getEmployee().getFirstName() + " " + invoice.getEmployee().getLastName();
            case 2:
                return invoice.getClient().getFirstName() + " " + invoice.getClient().getLastName();
            case 3:
                return invoice.getTotalValue();
            case 4: {
                if (invoice.isProcessed()) {
                    ImageIcon icon = new ImageIcon("resources/check.png");
                    return icon;
                } else {
                    ImageIcon icon = new ImageIcon("resources/close.png");
                    return icon;
                }
            }
            default:
                return "N/A";
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    public void updateTable(List<Invoice> invoices) {
        this.invoices = invoices;
        fireTableDataChanged();
    }
    
    public void removeInvoice(int row) {
        invoices.remove(row);
        fireTableDataChanged();
    }
    
    public Invoice getInvoice(int row) {
        return invoices.get(row);
    }
    
    public void add(Invoice invoice) {
        invoices.add(invoice);
        fireTableRowsInserted(invoices.size() - 1, invoices.size() - 1);
    }
    
    public void changeInvoice(Invoice invoice) {
        try {
            for (Invoice inv : invoices) {
                if (inv.getId() == invoice.getId()) {
                    inv.setClient(invoice.getClient());
                    inv.setDate(invoice.getDate());
                    inv.setEmployee(invoice.getEmployee());
                    inv.setItems(invoice.getItems());
                    inv.setProcessed(invoice.isProcessed());
                    inv.setTotalValue(invoice.getTotalValue());
                    break;
                }
            }
            fireTableDataChanged();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
