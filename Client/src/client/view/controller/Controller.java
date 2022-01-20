/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view.controller;

import client.communication.Communication;
import client.listeners.NotificationListener;
import client.listeners.ServerStoppedListener;
import client.view.form.FormMain;
import commonlib.communication.Operations;
import commonlib.communication.Request;
import commonlib.communication.Response;
import commonlib.communication.ResponseType;
import commonlib.domain.Client;
import commonlib.domain.Employee;
import commonlib.domain.Invoice;
import commonlib.domain.MeasurementUnit;
import commonlib.domain.Medicine;
import commonlib.domain.Operation;
import java.awt.Color;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author ANA
 */
public class Controller {

    private static Controller instance;
    private Employee currentEmployee;
    private ServerStoppedListener serverStoppedListener;
    private List<NotificationListener> notificationListeners;

    private Controller() {
        notificationListeners = new LinkedList<>();
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void setNotificationListener(NotificationListener notificationListener) {
        notificationListeners.add(notificationListener);
    }

    public void setServerStoppedListener(ServerStoppedListener serverStoppedListener) {
        this.serverStoppedListener = serverStoppedListener;
    }

    public void finish() {
        if (serverStoppedListener != null) {
            serverStoppedListener.serverStopped();
        }
    }

    public Employee login(String username, String password) throws Exception {
        Employee employee = new Employee();
        employee.setUsername(username);
        employee.setPassword(password);
        Request request = new Request(Operations.LOGIN, employee);
        Response response = Communication.getInstance().login(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            employee = (Employee) response.getResult();
            currentEmployee = employee;
            return employee;
        } else {
            throw response.getException();
        }
    }

    public void logout() throws Exception {
        Communication.getInstance().logout();
    }

    public Employee getCurrentEmployee() {
        return currentEmployee;
    }

    public void changePassword(Employee employee) throws Exception {
        Request request = new Request(Operations.CHANGE_EMPLOYEE_PASSWORD, employee);
        Response response = Communication.getInstance().changePassword(request);
        if (response.getResponseType().equals(ResponseType.ERROR)) {
            throw response.getException();
        }
    }

    public List<Medicine> getAllMedicines() throws Exception {
        Response response = Communication.getInstance().getAllMedicines();
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Medicine>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public void updateMedicine(Medicine medicine) throws Exception {
        Request request = new Request(Operations.UPDATE_MEDICINE, medicine);
        Response response = Communication.getInstance().updateMedicine(request);
        if (response.getResponseType().equals(ResponseType.ERROR)) {
            throw response.getException();
        }
    }

    public void deleteMedicine(Medicine medicine) throws Exception {
        Request request = new Request(Operations.DELETE_MEDICINE, medicine);

        Response response = Communication.getInstance().deleteMedicine(request);
        if (response.getResponseType().equals(ResponseType.ERROR)) {
            throw response.getException();
        }
    }

    public Medicine createMedicine(String name, BigDecimal price, BigDecimal avQuantity, MeasurementUnit mu) throws Exception {
        Medicine medicine = new Medicine();
        medicine.setName(name);
        medicine.setPrice(price);
        medicine.setAvailableQuantity(avQuantity);
        medicine.setMeasurementUnit(mu);
        Request request = new Request(Operations.CREATE_MEDICINE, medicine);
        try {
            Response response = Communication.getInstance().createMedicine(request);
            if (response.getResponseType().equals(ResponseType.SUCCESS)) {
                medicine = (Medicine) response.getResult();
                return medicine;
            } else {
                throw response.getException();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public void deleteOperation(Operation operation) throws Exception {
        Request request = new Request(Operations.DELETE_OPERATION, operation);
        try {
            Response response = Communication.getInstance().deleteOperation(request);
            if (response.getResponseType().equals(ResponseType.ERROR)) {
                throw response.getException();
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void updateOperation(Operation operation) throws Exception {

        Request request = new Request(Operations.UPDATE_OPERATION, operation);
        try {
            Response response = Communication.getInstance().updateOperation(request);
            if (response.getResponseType().equals(ResponseType.ERROR)) {
                throw response.getException();
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Operation createOperation(Operation operation) throws Exception {
        Request request = new Request(Operations.CREATE_OPERATION, operation);
        try {
            Response response = Communication.getInstance().createOperation(request);
            if (response.getResponseType().equals(ResponseType.SUCCESS)) {
                operation = (Operation) response.getResult();
                return operation;
            } else {
                throw response.getException();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public Operation getOperationById(long id) throws Exception {
        Request request = new Request(Operations.GET_OPERATION_BY_ID, id);
        try {
            Response response = Communication.getInstance().getOperationById(request);
            if (response.getResponseType().equals(ResponseType.SUCCESS)) {
                Operation operation = (Operation) response.getResult();
                return operation;
            } else {
                throw response.getException();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

    }

    public void saveInvoice(Invoice invoice) throws Exception {
        Request request = new Request(Operations.SAVE_INVOICE, invoice);
        try {
            Response response = Communication.getInstance().saveInvoice(request);
            if (response.getResponseType().equals(ResponseType.ERROR)) {
                throw response.getException();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public List<Client> getAllClients() throws Exception {
        Response response = Communication.getInstance().getAllClients();
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Client>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public List<Invoice> getAllInvoices() throws Exception {
        Response response = Communication.getInstance().getAllInvoices();
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Invoice>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public List<Invoice> getInvoicesByDate(String date) throws Exception {
        Response response = Communication.getInstance().getInvoicesByDate(date);
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Invoice>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public void updateInvoice(Invoice invoice) throws Exception {
        Request request = new Request(Operations.UPDATE_INVOICE, invoice);
        try {
            Response response = Communication.getInstance().updateInvoice(request);
            if (response.getResponseType().equals(ResponseType.ERROR)) {
                throw response.getException();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public void notifyNewMedicine(Medicine medicine) {
        for (NotificationListener notificationListener : notificationListeners) {
            notificationListener.newMedicineAdded(medicine);
        }
    }

    public void notifyChangedInvoice(Invoice changedInvoice) {
        for (NotificationListener notificationListener : notificationListeners) {
            notificationListener.invoiceChanged(changedInvoice);
        }
    }
    
    public void notifyNewInvoice(Invoice invoice) {
        for (NotificationListener notificationListener : notificationListeners) {
            notificationListener.newInvoiceAdded(invoice);
        }
    }
    
    public void notifyChangedMedicine(Medicine medicine) {
        for (NotificationListener notificationListener : notificationListeners) {
            notificationListener.changedMedicine(medicine);
        }
    }
    
    public void notifyDeletedMedicine(Medicine medicine) {
        for (NotificationListener notificationListener : notificationListeners) {
            notificationListener.deletedMedicine(medicine);
        }
    }
}
