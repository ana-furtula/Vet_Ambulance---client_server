/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commonlib.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author ANA
 */
public class InvoiceItem implements Serializable, ModelElement{
    private int orderNo;
    private Invoice invoice;
    private BigDecimal quantity;
    private BigDecimal totalPrice;
    private Operation operation;
    private Medicine medicine;
    private BigDecimal itemPrice;

    public InvoiceItem() {
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }
    
    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.orderNo;
        hash = 47 * hash + Objects.hashCode(this.invoice);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InvoiceItem other = (InvoiceItem) obj;
        if (this.orderNo != other.orderNo) {
            return false;
        }
        if (this.invoice.getId()!=other.getInvoice().getId()) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "InvoiceItem{" + "orderNo=" + orderNo + ", invoice=" + invoice + ", quantity=" + quantity + ", price=" + totalPrice + ", operation=" + operation + ", medicine=" + medicine + '}';
    }
    
     @Override
    public String getAtrValues() {
        return invoice.getId() + ", " + orderNo + ", " +  totalPrice + ", " + itemPrice + ", " + quantity + ", " + ((operation != null)? operation.getId(): "(NULL)") + ", " + ((medicine != null)? medicine.getId(): "(NULL)");
    }

    @Override
    public String setAtrValues() {
        return "quantity = " + quantity + ", totalPrice = " + totalPrice;
    }

    @Override
    public String getDbChangeableColumns() {
        return "invoiceId, orderNo, totalPrice, itemPrice, quantity, operationId, medicineId";
    }

    @Override
    public String getTableName() {
        return "invoice_items";
    }

    @Override
    public String getWhereCondition() {
        return "invoiceId = " + invoice.getId() + " AND orderNo = " + orderNo;
    }

    @Override
    public String getNameByColumn(int column) {
        String[] names = new String[]{"invoiceId", "orderNo", "price", "itemPrice", "quantity", "operationId", "medicineId"};
        return names[column];
    }

    @Override
    public ModelElement getNewRecord(ResultSet rs) throws SQLException {
        throw new NotImplementedException();
    }

}
