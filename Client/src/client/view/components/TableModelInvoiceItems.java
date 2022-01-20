/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view.components;

import commonlib.domain.Invoice;
import commonlib.domain.InvoiceItem;
import java.math.BigDecimal;
import javax.swing.table.AbstractTableModel;
import client.listeners.TableListener;

/**
 *
 * @author ANA
 */
public class TableModelInvoiceItems extends AbstractTableModel {

    private final Invoice invoice;
    private final String[] columnNames = new String[]{"Broj", "Proizvod/Usluga", "Cijena", "Količina", "Ukupno"};
    private final Class[] classNames = new Class[]{Integer.class, Object.class, BigDecimal.class, BigDecimal.class, BigDecimal.class};
    private int[] editableCells;
    private TableListener tableListener;

    public TableModelInvoiceItems(Invoice invoice) {
        this.invoice = invoice;
    }

    public void setTableErrorListener(TableListener tableErrorListener) {
        this.tableListener = tableErrorListener;
    }

    @Override
    public int getRowCount() {
        if (invoice != null) {
            return invoice.getItems().size();
        }
        return 0;
    }

    public Class[] getClassNames() {
        return classNames;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return classNames[columnIndex];
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        InvoiceItem item = invoice.getItems().get(rowIndex);
        switch (columnIndex) {
            case 3:
                try {
                    BigDecimal newValue = new BigDecimal(aValue.toString());
                    if (item.getQuantity().compareTo(newValue) == 0) {
                        return;
                    }
                    BigDecimal change;
                    if (item.getQuantity().compareTo(newValue) < 0) {
                        change = newValue.subtract(item.getQuantity());
                        if (item.getMedicine() != null) {
                            if (item.getMedicine().getAvailableQuantity().compareTo(change) > 0) {
                                item.getMedicine().setAvailableQuantity(item.getMedicine().getAvailableQuantity().subtract(change));
                            } else {
                                if(tableListener!=null){
                                    tableListener.errorHappened("Ne postoji dovoljno lijekova na zalihama.");
                                }
                                return;
                            }
                        }
                    } else {
                        change = item.getQuantity().subtract(newValue);
                        if (item.getMedicine() != null) {
                            item.getMedicine().setAvailableQuantity(item.getMedicine().getAvailableQuantity().add(change));
                        }
                    }
                    item.setQuantity(newValue);
                    invoice.setTotalValue(invoice.getTotalValue().subtract(item.getPrice()));
                    if (item.getMedicine() != null) {
                        item.setPrice(newValue.multiply(item.getMedicine().getPrice()));
                    } else {
                        item.setPrice(newValue.multiply(item.getOperation().getPrice()));
                    }
                    invoice.setTotalValue(invoice.getTotalValue().add(item.getPrice()));
                    fireTableRowsUpdated(rowIndex, rowIndex);
                    if(tableListener!=null){
                        tableListener.valueChanged();
                    }
                } catch (Exception ex) {
                }
                break;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceItem item = invoice.getItems().get(rowIndex);

        switch (columnIndex) {
            case 0:
                return item.getOrderNo();
            case 1:
                if (item.getMedicine() != null) {
                    return item.getMedicine();
                }
                return item.getOperation();
            case 2:
                if (item.getMedicine() != null) {
                    return item.getMedicine().getPrice();
                }
                return item.getOperation().getPrice();
            case 3:
                return item.getQuantity();
            case 4:
                return item.getPrice();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (editableCells == null || editableCells.length < 1) {
            return false;
        }
        for (int editableCell : editableCells) {
            if (editableCell == columnIndex) {
                return true;
            }
        }
        return false;
    }

    public void addInvoiceItem(InvoiceItem item) {
        item.setOrderNo(invoice.getItems().size() + 1);
        invoice.getItems().add(item);
        // TODO: azurirati total za invoice
        invoice.setTotalValue(invoice.getTotalValue().add(item.getPrice()));
        fireTableRowsInserted(invoice.getItems().size() - 1, invoice.getItems().size() - 1);
    }

    public void removeInvoiceItem(int row) {
        InvoiceItem item = invoice.getItems().get(row);
        invoice.getItems().remove(item);
        invoice.setTotalValue(invoice.getTotalValue().subtract(item.getPrice()));
        fireTableRowsDeleted(row, row);
    }

    public InvoiceItem getDataForRow(int row) {
        return this.invoice.getItems().get(row);
    }

    public void setEditableCells(int[] editableCells) {
        this.editableCells = editableCells;
    }

}
