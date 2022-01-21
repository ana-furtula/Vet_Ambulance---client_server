/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.listeners;

import commonlib.domain.Invoice;
import commonlib.domain.Medicine;
import commonlib.domain.Operation;

/**
 *
 * @author ANA
 */
public interface NotificationListener {
    default void newMedicineAdded(Medicine medicine){}
    default void changedMedicine(Medicine medicine){}
    default void deletedMedicine(Medicine medicine){}
    default void newInvoiceAdded(Invoice invoice){}
    default void invoiceChanged(Invoice invoice){}
    default void newOperationAdded(Operation operation){}
    default void changedOperation(Operation operation){}
    default void deletedOperation(Operation operation){}
}
