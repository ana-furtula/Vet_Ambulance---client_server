/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commonlib.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author ANA
 */
public class InvoiceItem implements Serializable{
    private int orderNo;
    private Invoice invoice;
    private BigDecimal quantity;
    private BigDecimal price;
    private Operation operation;
    private Medicine medicine;

    public InvoiceItem() {
    }

    public InvoiceItem(int orderNo, Invoice invoice, BigDecimal quantity, BigDecimal price, Operation operation, Medicine medicine) {
        this.orderNo = orderNo;
        this.invoice = invoice;
        this.quantity = quantity;
        this.price = price;
        this.operation = operation;
        this.medicine = medicine;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
        return "InvoiceItem{" + "orderNo=" + orderNo + ", invoice=" + invoice + ", quantity=" + quantity + ", price=" + price + ", operation=" + operation + ", medicine=" + medicine + '}';
    }

}
