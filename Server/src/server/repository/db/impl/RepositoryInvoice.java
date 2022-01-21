/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.repository.db.impl;

import commonlib.domain.Client;
import commonlib.domain.Employee;
import commonlib.domain.Invoice;
import commonlib.domain.InvoiceItem;
import commonlib.domain.MeasurementUnit;
import commonlib.domain.Medicine;
import commonlib.domain.Operation;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import server.repository.db.DbConnectionFactory;
import server.repository.db.DbRepository;

/**
 *
 * @author ANA
 */
public class RepositoryInvoice implements DbRepository<Invoice, Long> {

    Connection connection;

    @Override
    public List<Invoice> getAll() throws Exception {
        try {
            List<Invoice> invoices = new LinkedList<>();
            String upit = "SELECT i.id, i.date, i.totalValue, i.processed, e.id, e.firstname, e.lastname, e.jmbg, c.id, c.firstname, c.lastname, c.jmbg FROM invoices i INNER JOIN employees e ON (i.employeeId = e.id) INNER JOIN clients c ON (i.clientId = c.id)";
            connection = DbConnectionFactory.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(upit);

            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getLong("c.id"));
                client.setFirstName(rs.getString("c.firstname"));
                client.setLastName(rs.getString("c.lastname"));
                client.setJMBG(rs.getString("c.jmbg"));

                Employee employee = new Employee();
                employee.setId(rs.getLong("e.id"));
                employee.setFirstName(rs.getString("e.firstname"));
                employee.setLastName(rs.getString("e.lastname"));
                employee.setJMBG(rs.getString("e.jmbg"));

                Invoice invoice = new Invoice();
                invoice.setId(rs.getLong("i.id"));
                invoice.setDate(rs.getDate("i.date").toLocalDate());
                invoice.setProcessed(Boolean.valueOf(rs.getString("i.processed")));
                invoice.setTotalValue(rs.getBigDecimal("i.totalValue"));
                invoice.setClient(client);
                invoice.setEmployee(employee);

                invoices.add(invoice);
            }
            rs.close();
            statement.close();
            return invoices;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    private void addInvoiceItem(InvoiceItem item) throws Exception {
        connection = DbConnectionFactory.getInstance().getConnection();

        String query = "INSERT INTO invoice_items (invoiceId, orderNo, totalPrice, itemPrice, quantity, operationId, medicineId) VALUES(?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setLong(1, item.getInvoice().getId());
        statement.setLong(2, item.getOrderNo());
        statement.setBigDecimal(3, item.getTotalPrice());
        statement.setBigDecimal(4, item.getItemPrice());
        statement.setBigDecimal(5, item.getQuantity());
        if (item.getOperation() != null) {
            statement.setLong(6, item.getOperation().getId());
        } else {
            statement.setNull(6, Types.BIGINT);
        }
        if (item.getMedicine() != null) {
            statement.setLong(7, item.getMedicine().getId());
        } else {
            statement.setNull(7, Types.BIGINT);
        }
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void add(Invoice invoice) throws Exception {
        connection = DbConnectionFactory.getInstance().getConnection();

        String query = "INSERT INTO invoices (date, totalValue, processed, clientId, employeeId) VALUES (?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        statement.setDate(1, Date.valueOf(invoice.getDate()));
        statement.setBigDecimal(2, invoice.getTotalValue());
        statement.setString(3, String.valueOf(invoice.isProcessed()));
        statement.setLong(4, invoice.getClient().getId());
        statement.setLong(5, invoice.getEmployee().getId());

        statement.executeUpdate();
        ResultSet rsKey = statement.getGeneratedKeys();

        if (rsKey.next()) {
            Long invoiceID = rsKey.getLong(1);
            invoice.setId(invoiceID);

            for (InvoiceItem item : invoice.getItems()) {
                item.setInvoice(invoice);
                addInvoiceItem(item);
            }
        } else {
            throw new Exception("Invoice id is not generated");
        }
        statement.close();
    }

    @Override
    public void edit(Invoice invoice) throws Exception {
        try {
            String upit = "UPDATE invoices SET totalValue = " + invoice.getTotalValue() + ", processed = '" + String.valueOf(invoice.isProcessed()) + "'" + " WHERE id = " + invoice.getId();
            connection = DbConnectionFactory.getInstance().getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(upit);
            List<InvoiceItem> items = getInvoiceItems(invoice.getId());
            for (InvoiceItem invoiceItem : items) {
                if (!invoice.getItems().contains(invoiceItem)) {
                    upit = "DELETE FROM invoice_items WHERE invoiceId = " + invoice.getId() + " AND orderNo = " + invoiceItem.getOrderNo();
                    statement.executeUpdate(upit);
                }
            }
            for (InvoiceItem item : invoice.getItems()) {
                upit = "UPDATE invoice_items SET quantity = " + item.getQuantity() + ", totalPrice = " + item.getTotalPrice() + " WHERE invoiceId = " + invoice.getId() + " AND orderNo = " + item.getOrderNo();
                statement.executeUpdate(upit);
            }
            for (InvoiceItem item : invoice.getItems()) {
                if (!items.contains(item)) {
                    addInvoiceItem(item);
                }
            }

            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void delete(Invoice t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Invoice getById(Long k) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<InvoiceItem> getInvoiceItems(long id) throws Exception {
        try {
            List<InvoiceItem> items = new ArrayList<>();
            String upit = "SELECT i.orderNo, i.totalPrice, i.itemPrice, i.quantity, i.operationId, i.medicineId, m.id, m.name, m.measurementUnit, m.availableQuantity, m.price, o.id, o.name, o.price FROM invoice_items i LEFT JOIN medicines m ON(i.medicineId = m.id) LEFT JOIN operations o ON(i.operationId = o.id) WHERE i.invoiceId = " + id;
            connection = DbConnectionFactory.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(upit);

            while (rs.next()) {
                InvoiceItem item = new InvoiceItem();
                Invoice inv = new Invoice();
                inv.setId(id);
                item.setInvoice(inv);
                item.setOrderNo(rs.getInt("i.orderNo"));
                item.setTotalPrice(rs.getBigDecimal("i.totalPrice"));
                item.setItemPrice(rs.getBigDecimal("i.itemPrice"));
                item.setQuantity(rs.getBigDecimal("i.quantity"));
                Medicine m;
                try {
                    m = new Medicine();
                    m.setId(rs.getLong("m.id"));
                    m.setName(rs.getString("m.name"));
                    m.setMeasurementUnit(MeasurementUnit.valueOf(rs.getString("m.measurementUnit")));
                    m.setAvailableQuantity(rs.getBigDecimal("m.availableQuantity"));
                    m.setPrice(rs.getBigDecimal("m.price"));
                } catch (Exception ex) {
                    m = null;
                }
                item.setMedicine(m);
                Operation o;
                try {
                    o = new Operation();
                    o.setId(rs.getLong("o.id"));
                    o.setName(rs.getString("o.name"));
                    o.setPrice(rs.getBigDecimal("o.price"));
                } catch (Exception ex) {
                    o = null;
                }
                item.setOperation(o);

                items.add(item);
            }
            rs.close();
            statement.close();
            return items;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public BigDecimal getQuantity(InvoiceItem item) throws Exception {
        try {
            String upit = "SELECT quantity FROM invoice_items WHERE invoiceId = " + item.getInvoice().getId() + " AND orderNo = " + item.getOrderNo();
            connection = DbConnectionFactory.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(upit);
            BigDecimal quantity = null;
            if(rs.next()){
                quantity = rs.getBigDecimal("quantity");
            } else{
                throw new Exception("InvoiceItem not found.");
            }
            rs.close();
            statement.close();
            return quantity;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

}
