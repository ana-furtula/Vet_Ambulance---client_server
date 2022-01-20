/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commonlib.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author ANA
 */
public class Invoice implements Serializable{

    private long id;
    private LocalDate date;
    private BigDecimal totalValue;
    private boolean processed;
    private Client client;
    private Employee employee;
    private List<InvoiceItem> items;

    public Invoice() {
        items = new ArrayList<>();
        date = LocalDate.now();
        processed = false;
        totalValue = BigDecimal.ZERO;
        client = new Client();
        employee = new Employee();
    }

    public Invoice(long id, LocalDate date, BigDecimal totalValue, boolean processed, Client client, Employee employee, List<InvoiceItem> items) {
        this.id = id;
        this.date = date;
        this.totalValue = totalValue;
        this.processed = processed;
        this.client = client;
        this.employee = employee;
        this.items = items;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 43 * hash + Objects.hashCode(this.date);
        hash = 43 * hash + Objects.hashCode(this.totalValue);
        hash = 43 * hash + (this.processed ? 1 : 0);
        hash = 43 * hash + Objects.hashCode(this.client);
        hash = 43 * hash + Objects.hashCode(this.employee);
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
        final Invoice other = (Invoice) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.processed != other.processed) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.totalValue, other.totalValue)) {
            return false;
        }
        if (!Objects.equals(this.client, other.client)) {
            return false;
        }
        if (!Objects.equals(this.employee, other.employee)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Invoice{" + "id=" + id + ", date=" + date + ", totalValue=" + totalValue + ", processed=" + processed + ", client=" + client + ", employee=" + employee + '}';
    }

}
