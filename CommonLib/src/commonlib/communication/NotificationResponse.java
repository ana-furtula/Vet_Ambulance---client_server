/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commonlib.communication;

import commonlib.domain.Invoice;
import commonlib.domain.Medicine;
import java.io.Serializable;

/**
 *
 * @author ANA
 */
public class NotificationResponse implements Serializable{
    private Invoice newInvoice;
    private Invoice changedInvoice;
    private Medicine newMedicine;
    private Medicine deletedMedicine;
    private Medicine changedMedicine;
    
    public NotificationResponse() {
    }

    public Medicine getChangedMedicine() {
        return changedMedicine;
    }

    public void setChangedMedicine(Medicine changedMedicine) {
        this.changedMedicine = changedMedicine;
    }
    
    

    public Medicine getDeletedMedicine() {
        return deletedMedicine;
    }

    public void setDeletedMedicine(Medicine deletedMedicine) {
        this.deletedMedicine = deletedMedicine;
    }
    
    public Invoice getNewInvoice() {
        return newInvoice;
    }

    public void setNewInvoice(Invoice newInvoice) {
        this.newInvoice = newInvoice;
    }

    public Medicine getNewMedicine() {
        return newMedicine;
    }

    public void setNewMedicine(Medicine newMedicine) {
        this.newMedicine = newMedicine;
    }

    public Invoice getChangedInvoice() {
        return changedInvoice;
    }

    public void setChangedInvoice(Invoice changedInvoice) {
        this.changedInvoice = changedInvoice;
    }
    
    
}
