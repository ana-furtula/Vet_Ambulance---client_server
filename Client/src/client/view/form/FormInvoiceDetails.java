/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view.form;

import client.listeners.NotificationListener;
import client.view.components.TableModelInvoiceItems;
import client.view.controller.Controller;
import commonlib.domain.Invoice;
import java.time.format.DateTimeFormatter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import client.listeners.TableListener;
import client.validation.ValidationException;
import client.validation.Validator;
import client.view.components.PanelInvoiceItemMedicine;
import client.view.components.PanelInvoiceItemOperation;
import client.view.components.TableModelInvoices;
import commonlib.domain.InvoiceItem;
import commonlib.domain.Medicine;
import commonlib.domain.Operation;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author ANA
 */
public class FormInvoiceDetails extends javax.swing.JDialog {

    Invoice invoice;
    Invoice changeableInvoice;
    private PanelInvoiceItemMedicine pnlMedicine;
    private PanelInvoiceItemOperation pnlOperation;

    /**
     * Creates new form FormInvoiceDetails
     */
    public FormInvoiceDetails(java.awt.Dialog parent, boolean modal, Invoice invoice) {
        super(parent, modal);
        initComponents();
        this.invoice = invoice;
        try {
            prepareView();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Došlo je do greške prilikom inicijalizacije pogleda.", "Greška", JOptionPane.ERROR_MESSAGE);
        }

        Controller.getInstance().setNotificationListener(new NotificationListener() {
            @Override
            public void invoiceChanged(Invoice invoice) {
                try {
                    if (invoice.getId() == getInvoice().getId()) {
                        getInvoice().setItems(invoice.getItems());
                        getInvoice().setProcessed(invoice.isProcessed());
                        getInvoice().setTotalValue(invoice.getTotalValue());
                        getInvoice().setEmployee(invoice.getEmployee());
                        getInvoice().setDate(invoice.getDate());

                        getChangeableInvoice().setItems(invoice.getItems());
                        getChangeableInvoice().setProcessed(invoice.isProcessed());
                        getChangeableInvoice().setTotalValue(invoice.getTotalValue());
                        getChangeableInvoice().setEmployee(invoice.getEmployee());
                        getChangeableInvoice().setDate(invoice.getDate());
                        fillForm();
                    }
                } catch (Exception ex) {

                }
            }

            @Override
            public void changedMedicine(Medicine medicine) {
                try {
                    for (InvoiceItem item : getInvoice().getItems()) {
                        if (item.getMedicine() != null && item.getMedicine().getId().equals(medicine.getId())) {
                            item.setMedicine(medicine);
                        }
                    }
                    for (InvoiceItem item : getChangeableInvoice().getItems()) {
                        if (item.getMedicine() != null && item.getMedicine().getId().equals(medicine.getId())) {
                            item.setMedicine(medicine);
                        }
                    }
                    TableModelInvoiceItems model = (TableModelInvoiceItems) tblItems.getModel();
                    model.fireTableDataChanged();
                } catch (Exception ex) {
                }
            }

            @Override
            public void changedOperation(Operation operation) {
                try {
                    for (InvoiceItem item : getInvoice().getItems()) {
                        if (item.getOperation() != null && item.getOperation().getId().equals(operation.getId())) {
                            item.setOperation(operation);
                        }
                    }
                    for (InvoiceItem item : getInvoice().getItems()) {
                        if (item.getOperation() != null && item.getOperation().getId().equals(operation.getId())) {
                            item.setOperation(operation);
                        }
                    }
                    TableModelInvoiceItems model = (TableModelInvoiceItems) tblItems.getModel();
                    model.fireTableDataChanged();
                } catch (Exception ex) {

                }
            }
        });
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public Invoice getChangeableInvoice() {
        return changeableInvoice;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        radioGroupInvoiceItem = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtDate = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtEmployee = new javax.swing.JTextField();
        txtClient = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblItems = new javax.swing.JTable();
        btnDelete = new javax.swing.JButton();
        cbProcessed = new javax.swing.JCheckBox();
        btnSave = new javax.swing.JButton();
        pnlNewInvoiceItemMain = new javax.swing.JPanel();
        radioMedicine = new javax.swing.JRadioButton();
        radioOperation = new javax.swing.JRadioButton();
        pnlNewInvoiceItem = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("ID:");

        jLabel2.setText("Datum:");

        txtID.setEditable(false);

        txtDate.setEditable(false);

        jLabel3.setText("Zaposleni:");

        txtEmployee.setEditable(false);

        txtClient.setEditable(false);

        jLabel4.setText("Klijent:");

        txtTotal.setEditable(false);

        jLabel5.setText("Ukupno:");

        tblItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblItems);

        btnDelete.setText("Ukloni");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        cbProcessed.setText("Obrađen");

        btnSave.setText("Sačuvaj");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        pnlNewInvoiceItemMain.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nova stavka računa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13))); // NOI18N

        radioMedicine.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        radioMedicine.setText("Lijek");
        radioMedicine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioMedicineActionPerformed(evt);
            }
        });

        radioOperation.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        radioOperation.setText("Operacija");
        radioOperation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioOperationActionPerformed(evt);
            }
        });

        pnlNewInvoiceItem.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout pnlNewInvoiceItemMainLayout = new javax.swing.GroupLayout(pnlNewInvoiceItemMain);
        pnlNewInvoiceItemMain.setLayout(pnlNewInvoiceItemMainLayout);
        pnlNewInvoiceItemMainLayout.setHorizontalGroup(
            pnlNewInvoiceItemMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNewInvoiceItemMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlNewInvoiceItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnlNewInvoiceItemMainLayout.createSequentialGroup()
                .addContainerGap(117, Short.MAX_VALUE)
                .addComponent(radioMedicine, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(radioOperation, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85))
        );
        pnlNewInvoiceItemMainLayout.setVerticalGroup(
            pnlNewInvoiceItemMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNewInvoiceItemMainLayout.createSequentialGroup()
                .addGroup(pnlNewInvoiceItemMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioOperation, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(radioMedicine, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlNewInvoiceItem, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnAdd.setText("Dodaj");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtClient, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEmployee, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlNewInvoiceItemMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(cbProcessed)
                                .addGap(57, 57, 57)
                                .addComponent(btnSave))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(pnlNewInvoiceItemMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(btnAdd)))
                        .addGap(0, 31, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbProcessed)
                            .addComponent(btnSave))
                        .addGap(43, 43, 43))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int selected = tblItems.getSelectedRow();
        if (selected != -1) {
            TableModelInvoiceItems tm = (TableModelInvoiceItems) tblItems.getModel();
            InvoiceItem item = tm.getDataForRow(selected);
            tm.removeInvoiceItem(selected);
            if (item.getMedicine() != null) {
                pnlMedicine.updateAvailableQuantity(pnlMedicine.getAvailableQuantity(item.getMedicine()).add(item.getQuantity()), item.getMedicine());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Potrebno je selektovati stavku računa koju želite da obrišete", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        try {
            if (changeableInvoice.getItems().size() < 1) {
                JOptionPane.showMessageDialog(this, "Račun mora sadržati barem jednu stavku.", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }
//            invoice.setItems(changeableInvoice.getItems());
//            invoice.setTotalValue(changeableInvoice.getTotalValue());
//            invoice.setProcessed(cbProcessed.isSelected());
//            invoice.setEmployee(Controller.getInstance().getCurrentEmployee());
//            invoice.setDate(LocalDate.now());
            changeableInvoice.setId(invoice.getId());
            changeableInvoice.setProcessed(cbProcessed.isSelected());
            changeableInvoice.setEmployee(Controller.getInstance().getCurrentEmployee());
            changeableInvoice.setDate(LocalDate.now());
            changeableInvoice.setClient(invoice.getClient());
            Controller.getInstance().updateInvoice(changeableInvoice);
            JOptionPane.showMessageDialog(this, "Sistem je zapamtio račun.");
            invoice.setItems(changeableInvoice.getItems());
            invoice.setTotalValue(changeableInvoice.getTotalValue());
            invoice.setProcessed(cbProcessed.isSelected());
            invoice.setEmployee(Controller.getInstance().getCurrentEmployee());
            invoice.setDate(LocalDate.now());
            this.dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        TableModelInvoiceItems tm = (TableModelInvoiceItems) tblItems.getModel();
        if (radioGroupInvoiceItem.getSelection().getActionCommand().equals("Medicine")) {
            try {
                Medicine medicine = pnlMedicine.getSelectedMedicine();
                String quantity = pnlMedicine.getSelectedQuantity();
                Validator.startValidation()
                        .validateNotNullOrEmpty(quantity, "Količina je obavezno polje. ")
                        .validateValueIsNumber(quantity, "Količina mora biti broj.")
                        .throwIfInvalide();
                BigDecimal qu = new BigDecimal(quantity);
                if (qu.compareTo(BigDecimal.ZERO) <= 0) {
                    JOptionPane.showMessageDialog(this, "Količina mora biti veća od nule.", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                BigDecimal leftQuantity = medicine.getAvailableQuantity().subtract(qu);
                if (leftQuantity.compareTo(BigDecimal.ZERO) < 0) {
                    JOptionPane.showMessageDialog(this, "Nema dovoljno količine lijeka " + medicine.getName() + " na zalihama.", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                pnlMedicine.updateAvailableQuantity(leftQuantity, medicine);
                InvoiceItem item = new InvoiceItem();
                item.setMedicine(medicine);
                BigDecimal quant = new BigDecimal(quantity);
                item.setTotalPrice(quant.multiply(medicine.getPrice()));
                item.setItemPrice(medicine.getPrice());
                item.setQuantity(quant);
                item.setInvoice(invoice);
                tm.addInvoiceItem(item);

            } catch (ValidationException ex) {
                pnlMedicine.setSelectedQuantityError(ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return;
        }
        if (radioGroupInvoiceItem.getSelection().getActionCommand().equals("Operation")) {
            try {
                Operation operation = pnlOperation.getSelectedOperation();
                InvoiceItem item = new InvoiceItem();
                item.setOperation(operation);
                item.setTotalPrice(operation.getPrice());
                item.setItemPrice(operation.getPrice());
                item.setQuantity(BigDecimal.ONE);
                item.setInvoice(invoice);

                tm.addInvoiceItem(item);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return;
        }
        JOptionPane.showMessageDialog(this, "Potrebno je selektovati stavku računa", "Greška", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_btnAddActionPerformed

    private void radioMedicineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioMedicineActionPerformed
        if (pnlOperation != null) {
            pnlOperation.setVisible(false);
        }
        if (pnlMedicine != null) {
            pnlMedicine.setVisible(true);
        } else {
            pnlMedicine = new PanelInvoiceItemMedicine();
            pnlMedicine.setVisible(true);
            pnlNewInvoiceItem.add(pnlMedicine);
        }
    }//GEN-LAST:event_radioMedicineActionPerformed

    private void radioOperationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioOperationActionPerformed
        if (pnlMedicine != null) {
            pnlMedicine.setVisible(false);
        }
        if (pnlOperation != null) {
            pnlOperation.setVisible(true);
        } else {
            pnlOperation = new PanelInvoiceItemOperation();
            pnlNewInvoiceItem.add(pnlOperation);
            pnlOperation.setVisible(true);
        }
    }//GEN-LAST:event_radioOperationActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private javax.swing.JCheckBox cbProcessed;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlNewInvoiceItem;
    private javax.swing.JPanel pnlNewInvoiceItemMain;
    private javax.swing.ButtonGroup radioGroupInvoiceItem;
    private javax.swing.JRadioButton radioMedicine;
    private javax.swing.JRadioButton radioOperation;
    private javax.swing.JTable tblItems;
    private javax.swing.JTextField txtClient;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtEmployee;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables

    private void prepareView() throws Exception {
        try {
            setChangeableInvoice();
            TableModelInvoiceItems model = new TableModelInvoiceItems(changeableInvoice);
            tblItems.setModel(model);
            DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tblItems.getDefaultRenderer(Object.class);
            renderer.setHorizontalAlignment(JLabel.LEFT);
            tblItems.setRowHeight(20);

            fillForm();

            radioMedicine.setActionCommand("Medicine");
            radioOperation.setActionCommand("Operation");
            radioMedicine.setSelected(true);
            radioOperation.setSelected(false);
            radioGroupInvoiceItem.add(radioMedicine);
            radioGroupInvoiceItem.add(radioOperation);

            pnlMedicine = new PanelInvoiceItemMedicine();
            pnlMedicine.setVisible(true);
            pnlNewInvoiceItem.add(pnlMedicine);

            model.setTableListener(new TableListener() {
                @Override
                public void errorHappened(String error) {
                    JOptionPane.showMessageDialog(FormInvoiceDetails.this, error, "Greška", JOptionPane.ERROR_MESSAGE);
                }

                @Override
                public void valueChanged(BigDecimal total) {
                    txtTotal.setText((String.valueOf(total)));
                }
            });

        } catch (Exception ex) {
            throw ex;
        }
    }

    public void fillForm() {
        txtID.setText(String.valueOf(invoice.getId()));
        txtDate.setText(invoice.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        txtEmployee.setText(invoice.getEmployee().toString());
        txtClient.setText(invoice.getClient().toString());
        txtTotal.setText(String.valueOf(invoice.getTotalValue()));
        TableModelInvoiceItems model = (TableModelInvoiceItems) tblItems.getModel();
        if (invoice.isProcessed()) {
            btnDelete.setEnabled(false);
            model.setEditableCells(null);
            cbProcessed.setSelected(true);
            cbProcessed.setEnabled(false);
            btnSave.setEnabled(false);
            btnAdd.setEnabled(false);
            pnlNewInvoiceItemMain.setVisible(false);
        } else {
            cbProcessed.setSelected(false);
            int[] editableCells = new int[]{3};
            model.setEditableCells(editableCells);
        }
        model.fireTableDataChanged();
    }

    private void setChangeableInvoice() {
        changeableInvoice = new Invoice();
        changeableInvoice.setId(invoice.getId());
        changeableInvoice.setProcessed(invoice.isProcessed());
        changeableInvoice.setTotalValue(invoice.getTotalValue());
        List<InvoiceItem> items = new LinkedList<>();
        for (InvoiceItem item : invoice.getItems()) {
            InvoiceItem ii = new InvoiceItem();
            ii.setInvoice(invoice);
            ii.setMedicine(item.getMedicine());
            ii.setOperation(item.getOperation());
            ii.setOrderNo(item.getOrderNo());
            ii.setTotalPrice(item.getTotalPrice());
            ii.setItemPrice(item.getItemPrice());
            ii.setQuantity(item.getQuantity());
            items.add(ii);
        }
        changeableInvoice.setItems(items);

    }
}
