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
import commonlib.domain.Medicine;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author ANA
 */
public class TableModelInvoiceItems extends AbstractTableModel {

    private Invoice invoice;
    private final String[] columnNames = new String[]{"Broj", "Proizvod/Usluga", "Cijena", "Količina", "Ukupno"};
    private final Class[] classNames = new Class[]{Integer.class, Object.class, BigDecimal.class, BigDecimal.class, BigDecimal.class};
    private int[] editableCells;
    private TableListener tableListener;

    public TableModelInvoiceItems(Invoice invoice) {
        this.invoice = invoice;
    }

    public void setTableListener(TableListener tableListener) {
        this.tableListener = tableListener;
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
                    if (newValue.compareTo(BigDecimal.ZERO) == 0) {
                        if (tableListener != null) {
                            tableListener.errorHappened("Količina mora biti veća od 0.");
                        }
                        return;
                    }
                    if (item.getQuantity().compareTo(newValue) == 0) {
                        return;
                    }
                    BigDecimal change;

                    if (item.getQuantity().compareTo(newValue) < 0) {
                        change = newValue.subtract(item.getQuantity());
                        if (item.getMedicine() != null) {
                            if (item.getMedicine().getAvailableQuantity().compareTo(change) >= 0) {
                                item.getMedicine().setAvailableQuantity(item.getMedicine().getAvailableQuantity().subtract(change));
                                if (!item.getMedicine().getPrice().equals(item.getItemPrice())) {
                                    InvoiceItem newItem = new InvoiceItem();
                                    newItem.setInvoice(invoice);
                                    newItem.setItemPrice(item.getMedicine().getPrice());
                                    newItem.setMedicine(item.getMedicine());
                                    newItem.setQuantity(change);
                                    newItem.setTotalPrice(newItem.getQuantity().add(newItem.getItemPrice()));
                                    addInvoiceItem(newItem);
                                    return;
                                }

                            } else {
                                if (tableListener != null) {
                                    tableListener.errorHappened("Ne postoji dovoljno lijekova na zalihama.");
                                }
                                return;
                            }
                        } else {

                            if (!item.getOperation().getPrice().equals(item.getItemPrice())) {
                                InvoiceItem newItem = new InvoiceItem();
                                newItem.setInvoice(item.getInvoice());
                                newItem.setItemPrice(item.getOperation().getPrice());
                                newItem.setOperation(item.getOperation());
                                newItem.setQuantity(change);
                                newItem.setTotalPrice(newItem.getQuantity().multiply(newItem.getItemPrice()));
                                addInvoiceItem(newItem);
                                return;
                            }
                        }
                        item.setQuantity(newValue);
                        item.setTotalPrice(item.getItemPrice().multiply(item.getQuantity()));
                        invoice.setTotalValue(invoice.getTotalValue().add(item.getItemPrice().multiply(change)));
                    } else {
                        change = item.getQuantity().subtract(newValue);
                        if (item.getMedicine() != null) {
                            item.getMedicine().setAvailableQuantity(item.getMedicine().getAvailableQuantity().add(change));
                            if (!item.getMedicine().getPrice().equals(item.getItemPrice())) {
                                InvoiceItem newItem = new InvoiceItem();
                                newItem.setInvoice(invoice);
                                newItem.setItemPrice(item.getMedicine().getPrice());
                                newItem.setMedicine(item.getMedicine());
                                newItem.setQuantity(change);
                                newItem.setTotalPrice(newItem.getQuantity().add(newItem.getItemPrice()));
                                addInvoiceItem(newItem);
                                return;
                            }
                        } else {
                            if (!item.getOperation().getPrice().equals(item.getItemPrice())) {
                                InvoiceItem newItem = new InvoiceItem();
                                newItem.setInvoice(invoice);
                                newItem.setItemPrice(item.getOperation().getPrice());
                                newItem.setOperation(item.getOperation());
                                newItem.setQuantity(change);
                                newItem.setTotalPrice(newItem.getQuantity().multiply(newItem.getItemPrice()));
                                addInvoiceItem(newItem);
                                return;
                            }
                        }
                        item.setQuantity(newValue);
                        item.setTotalPrice(item.getItemPrice().multiply(item.getQuantity()));
                        invoice.setTotalValue(invoice.getTotalValue().subtract(item.getItemPrice().multiply(change)));
                    }

                    fireTableRowsUpdated(rowIndex, rowIndex);
                    if (tableListener != null) {
                        tableListener.valueChanged(invoice.getTotalValue());
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
                return item.getItemPrice();
            case 3:
                return item.getQuantity();
            case 4:
                return item.getTotalPrice();
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
        int max = 0;
        for (InvoiceItem item1 : invoice.getItems()) {
            if (item1.getOrderNo() > max) {
                max = item1.getOrderNo();
            }
        }
        item.setOrderNo(max + 1);
        invoice.getItems().add(item);
        invoice.setTotalValue(invoice.getTotalValue().add(item.getTotalPrice()));
        if (tableListener != null) {
            tableListener.valueChanged(invoice.getTotalValue());
        }
        fireTableRowsInserted(invoice.getItems().size() - 1, invoice.getItems().size() - 1);
    }

    public void removeInvoiceItem(int row) {
        InvoiceItem item = invoice.getItems().get(row);
        invoice.getItems().remove(item);
        invoice.setTotalValue(invoice.getTotalValue().subtract(item.getTotalPrice()));
        if (tableListener != null) {
            tableListener.valueChanged(invoice.getTotalValue());
        }
        fireTableRowsDeleted(row, row);
    }

    public InvoiceItem getDataForRow(int row) {
        return this.invoice.getItems().get(row);
    }

    public void setEditableCells(int[] editableCells) {
        this.editableCells = editableCells;
    }

}
