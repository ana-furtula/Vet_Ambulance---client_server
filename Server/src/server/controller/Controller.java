/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import commonlib.domain.Client;
import commonlib.domain.Employee;
import commonlib.domain.Invoice;
import commonlib.domain.Medicine;
import commonlib.domain.Operation;
import java.util.LinkedList;
import java.util.List;
import server.components.InvoiceDate;
import server.so.AbstractSO;
import server.so.client.*;
import server.so.employee.ChangePasswordSO;
import server.so.employee.LoginSO;
import server.so.invoice.*;
import server.so.medicine.*;
import server.so.operation.*;

/**
 *
 * @author Milos
 */
public class Controller {

    private static Controller instance;
    private Employee currentUser;

    private Controller() {
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public Employee login(String username, String password) throws Exception {
        AbstractSO loginSO = new LoginSO();
        Employee employee = new Employee();
        employee.setUsername(username);
        employee.setPassword(password);
        loginSO.execute(employee);
        this.currentUser = employee;
        return employee;
    }

    public Employee getCurrentUser() {
        return currentUser;
    }

    public Employee changePassword(Employee employee) throws Exception {
        AbstractSO changePasswordSO = new ChangePasswordSO();
        changePasswordSO.execute(employee);
        this.currentUser = employee;
        return employee;
    }

    public void deleteMedicine(Medicine medicine) throws Exception {
        AbstractSO deleteMedicineSO = new DeleteMedicineSO();
        deleteMedicineSO.execute(medicine);
    }

    public List<Medicine> getAllMedicines() throws Exception {
        AbstractSO getAllMedicinesSO = new GetAllMedicinesSO();
        List<Medicine> medicines = new LinkedList<>();
        getAllMedicinesSO.execute(medicines);
        return medicines;
    }

    public void addMedicine(Medicine m) throws Exception {
        AbstractSO addMedicineSO = new AddMedicineSO();
        addMedicineSO.execute(m);
    }

    public void updateMedicine(Medicine m) throws Exception {
        AbstractSO updateMedicineSO = new UpdateMedicineSO();
        updateMedicineSO.execute(m);
    }

    public List<Operation> getAllOperations() throws Exception {
        AbstractSO getAllOperationsSO = new GetAllOperationsSO();
        List<Operation> operations = new LinkedList<>();
        getAllOperationsSO.execute(operations);
        return operations;
    }

    public Operation getOperationById(Long id) throws Exception {
        AbstractSO getByIdSO = new GetOperationByIdSO();
        Operation operation = new Operation();
        operation.setId(id);
        getByIdSO.execute(operation);
        return operation;
    }

    public void deleteOperation(Operation operation) throws Exception {
        AbstractSO deleteOperationSO = new DeleteOperationSO();
        deleteOperationSO.execute(operation);
    }

    public void updateOperation(Operation operation) throws Exception {
        AbstractSO updateOperationSO = new UpdateOperationSO();
        updateOperationSO.execute(operation);
    }

    public void addOperation(Operation operation) throws Exception {
        AbstractSO addOperationSO = new AddOperationSO();
        addOperationSO.execute(operation);
    }

    public List<Client> getAllClients() throws Exception {
        AbstractSO getAllClientsSO = new GetAllClientsSO();
        List<Client> clients = new LinkedList<>();
        getAllClientsSO.execute(clients);
        return clients;
    }

    public void addInvoice(Invoice invoice) throws Exception {
        AbstractSO addInvoiceSO = new AddInvoiceSO();
        addInvoiceSO.execute(invoice);
    }

    public List<Invoice> getAllInvoices() throws Exception {
        AbstractSO getAllInvoices = new GetAllInvoicesSO();
        List<Invoice> invoices = new LinkedList<>();
        getAllInvoices.execute(invoices);
        return (List<Invoice>) invoices;
    }

    public void updateInvoice(Invoice invoice) throws Exception {
        AbstractSO updateInvoiceSO = new UpdateInvoiceSO();
        updateInvoiceSO.execute(invoice);
    }

    public List<Invoice> getInvoicesByDate(String date) throws Exception {
        AbstractSO findInvoicesByDate = new FindInvoicesByDate();
        InvoiceDate invDate = new InvoiceDate();
        invDate.setDate(date);
        findInvoicesByDate.execute(invDate);
        return invDate.getInvoices();
    }

}
