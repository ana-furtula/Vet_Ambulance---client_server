/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.threads;

import commonlib.communication.NotificationResponse;
import commonlib.communication.Operations;
import commonlib.communication.Receiver;
import commonlib.communication.Request;
import commonlib.communication.Response;
import commonlib.communication.ResponseType;
import commonlib.communication.Sender;
import commonlib.domain.Client;
import commonlib.domain.Employee;
import commonlib.domain.Invoice;
import commonlib.domain.Medicine;
import commonlib.domain.Operation;
import commonlib.exception.ServerStoppedException;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import server.controller.Controller;
import server.so.medicine.DeleteMedicineSO;

/**
 *
 * @author ANA
 */
public class HandleClientThread extends Thread {

    private Socket socket;
    private Employee employee;
    private final ServerThread server;
    private Sender sender;
    private Receiver receiver;
    private boolean stop = false;
    private NotificationResponse notification;
    
    public HandleClientThread(Socket socket, ServerThread server) throws IOException {
        this.socket = socket;
        this.server = server;
        sender = new Sender(socket);
        receiver = new Receiver(socket);
        notification = new NotificationResponse();
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            try {
                Request request = (Request) new Receiver(socket).receive();
                Response response = handleRequest(request);
                if (response != null) {
                    sender.send(response);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public Employee getEmployee() {
        return employee;
    }

    private Response handleRequest(Request request) {
        try {
            int operation = request.getOperation();
            Response response = null;
            switch (operation) {
                case Operations.LOGIN:
                    response = login(request);
                    break;
                case Operations.GET_CURRENT_USER:
                    response = getCurrentUser();
                    break;
                case Operations.CHANGE_EMPLOYEE_PASSWORD:
                    response = changePassword(request);
                    break;
                case Operations.GET_ALL_MEDICINES:
                    response = getAllMedicines();
                    break;
                case Operations.DELETE_MEDICINE:
                    response = deleteMedicine(request);
                    break;
                case Operations.CREATE_MEDICINE:
                    response = createMedicine(request);
                    break;
                case Operations.UPDATE_MEDICINE:
                    response = updateMedicine(request);
                    break;
                case Operations.GET_ALL_OPERATIONS:
                    response = getAllOperations();
                    break;
                case Operations.DELETE_OPERATION:
                    response = deleteOperation(request);
                    break;
                case Operations.CREATE_OPERATION:
                    response = createOperation(request);
                    break;
                case Operations.UPDATE_OPERATION:
                    response = updateOperation(request);
                    break;
                case Operations.GET_OPERATION_BY_ID:
                    response = getOperationById(request);
                    break;
                case Operations.GET_ALL_CLIENTS:
                    response = getAllClients();
                    break;
                case Operations.SAVE_INVOICE:
                    response = saveInvoice(request);
                    break;
                case Operations.GET_ALL_INVOICES:
                    response = getAllInvoices();
                    break;
                case Operations.UPDATE_INVOICE:
                    response = updateInvoice(request);
                    break;
                case Operations.SERVER_STOPPED_ACK:
                    synchronized (server) {
                        server.notify();
                    }
                    this.socket.close();
                    break;
                case Operations.LOGOUT:
                    server.logoutHappened(this);
                    break;
                case Operations.UPDATES:
                    response = sendUpdates();
                    break;
                case Operations.GET_INVOICES_BY_DATE:
                    response = getInvoicesByDate(request);
                    break;
                default:
            }
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Response login(Request request) {
        Response response = new Response();
        Employee requestEmployee = (Employee) request.getArgument();
        {
            try {
                Employee employee = Controller.getInstance().login(requestEmployee.getUsername(), requestEmployee.getPassword());
                response.setResponseType(ResponseType.SUCCESS);
                response.setResult(employee);
                this.employee = employee;
                server.loginHappened(employee);
            } catch (Exception ex) {
                ex.printStackTrace();
                response.setException(ex);
                response.setResponseType(ResponseType.ERROR);
            }
        }
        return response;
    }

    private Response getCurrentUser() {
        Response response = new Response();
        {
            try {
                Employee employee = Controller.getInstance().getCurrentUser();
                response.setResponseType(ResponseType.SUCCESS);
                response.setResult(employee);
            } catch (Exception ex) {
                ex.printStackTrace();
                response.setException(ex);
                response.setResponseType(ResponseType.ERROR);
            }
        }
        return response;
    }

    private Response changePassword(Request request) {
        Response response = new Response();
        Employee requestEmployee = (Employee) request.getArgument();
        {
            try {
                Employee employee = Controller.getInstance().changePassword(requestEmployee);
                response.setResponseType(ResponseType.SUCCESS);
                response.setResult(employee);
            } catch (Exception ex) {
                ex.printStackTrace();
                response.setException(ex);
                response.setResponseType(ResponseType.ERROR);
            }
        }
        return response;
    }

    void serverStopped() {
        try {
            stop = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Response getAllMedicines() {
        Response response = new Response();
        {
            try {
                List<Medicine> medicines = Controller.getInstance().getAllMedicines();
                response.setResponseType(ResponseType.SUCCESS);
                response.setResult(medicines);
            } catch (Exception ex) {
                ex.printStackTrace();
                response.setException(ex);
                response.setResponseType(ResponseType.ERROR);
            }
        }
        return response;
    }

    private Response deleteMedicine(Request request) {
        Response response = new Response();
        {
            try {
                Medicine medicine = (Medicine) request.getArgument();
                Controller.getInstance().deleteMedicine(medicine);
                response.setResponseType(ResponseType.SUCCESS);
                server.notifyDeletedMedicine(this, medicine);
            } catch (Exception ex) {
                ex.printStackTrace();
                response.setException(new Exception(ex.getMessage()));
                response.setResponseType(ResponseType.ERROR);
            }
        }
        return response;
    }

    private Response createMedicine(Request request) {
        Response response = new Response();
        {
            try {
                Medicine medicine = (Medicine) request.getArgument();
                Controller.getInstance().addMedicine(medicine);
                response.setResponseType(ResponseType.SUCCESS);
                response.setResult(medicine);
                server.notifyNewMedicine(this, medicine);
            } catch (Exception ex) {
                ex.printStackTrace();
                response.setException(new Exception(ex.getMessage()));
                response.setResponseType(ResponseType.ERROR);
            }
        }
        return response;
    }

    private Response updateMedicine(Request request) {
        Response response = new Response();
        {
            try {
                Medicine medicine = (Medicine) request.getArgument();
                Controller.getInstance().updateMedicine(medicine);
                response.setResponseType(ResponseType.SUCCESS);
                server.notifyChangedMedicine(this, medicine);
            } catch (Exception ex) {
                ex.printStackTrace();
                response.setException(new Exception(ex.getMessage()));
                response.setResponseType(ResponseType.ERROR);
            }
        }
        return response;
    }

    private Response getAllOperations() {
        Response response = new Response();
        {
            try {
                List<Operation> operations = Controller.getInstance().getAllOperations();
                response.setResponseType(ResponseType.SUCCESS);
                response.setResult(operations);
            } catch (Exception ex) {
                ex.printStackTrace();
                response.setException(new Exception(ex.getMessage()));
                response.setResponseType(ResponseType.ERROR);
            }
        }
        return response;
    }

    private Response deleteOperation(Request request) {
        Response response = new Response();
        {
            try {
                Controller.getInstance().deleteOperation((Operation) request.getArgument());
                response.setResponseType(ResponseType.SUCCESS);
            } catch (Exception ex) {
                ex.printStackTrace();
                response.setException(new Exception(ex.getMessage()));
                response.setResponseType(ResponseType.ERROR);
            }
        }
        return response;
    }

    private Response createOperation(Request request) {
        Response response = new Response();
        {
            try {
                Operation operation = (Operation) request.getArgument();
                Controller.getInstance().addOperation(operation);
                response.setResponseType(ResponseType.SUCCESS);
                response.setResult(operation);
            } catch (Exception ex) {
                ex.printStackTrace();
                response.setException(new Exception(ex.getMessage()));
                response.setResponseType(ResponseType.ERROR);
            }
        }
        return response;
    }

    private Response updateOperation(Request request) {
        Response response = new Response();
        {
            try {
                Operation operation = (Operation) request.getArgument();
                Controller.getInstance().updateOperation(operation);
                response.setResponseType(ResponseType.SUCCESS);
            } catch (Exception ex) {
                ex.printStackTrace();
                response.setException(new Exception(ex.getMessage()));
                response.setResponseType(ResponseType.ERROR);
            }
        }
        return response;
    }

    private Response getOperationById(Request request) {
        Response response = new Response();
        {
            try {
                long id = (long) request.getArgument();
                Operation operation = Controller.getInstance().getOperationById(id);
                response.setResponseType(ResponseType.SUCCESS);
                response.setResult(operation);
            } catch (Exception ex) {
                ex.printStackTrace();
                response.setException(new Exception(ex.getMessage()));
                response.setResponseType(ResponseType.ERROR);
            }
        }
        return response;
    }

    private Response getAllClients() {
        Response response = new Response();
        {
            try {
                List<Client> clients = Controller.getInstance().getAllClients();
                response.setResponseType(ResponseType.SUCCESS);
                response.setResult(clients);
            } catch (Exception ex) {
                ex.printStackTrace();
                response.setException(new Exception(ex.getMessage()));
                response.setResponseType(ResponseType.ERROR);
            }
        }
        return response;
    }

    private Response saveInvoice(Request request) {
        Response response = new Response();
        {
            try {
                Invoice invoice = (Invoice) request.getArgument();
                Controller.getInstance().addInvoice(invoice);
                response.setResponseType(ResponseType.SUCCESS);
                response.setResult(invoice);
                server.notifyNewInvoice(this, invoice);
            } catch (Exception ex) {
                ex.printStackTrace();
                response.setException(new Exception(ex.getMessage()));
                response.setResponseType(ResponseType.ERROR);
            }
        }
        return response;
    }

    private Response getAllInvoices() {
        Response response = new Response();
        {
            try {
                List<Invoice> invoices = Controller.getInstance().getAllInvoices();
                response.setResponseType(ResponseType.SUCCESS);
                response.setResult(invoices);
            } catch (Exception ex) {
                ex.printStackTrace();
                response.setException(new Exception(ex.getMessage()));
                response.setResponseType(ResponseType.ERROR);
            }
        }
        return response;
    }

    private Response updateInvoice(Request request) {
        Response response = new Response();
        {
            try {
                Invoice invoice = (Invoice) request.getArgument();
                Controller.getInstance().updateInvoice(invoice);
                response.setResponseType(ResponseType.SUCCESS);
                response.setResult(invoice);
                server.notifyChangedInvoice(this, invoice);
            } catch (Exception ex) {
                ex.printStackTrace();
                response.setException(new Exception(ex.getMessage()));
                response.setResponseType(ResponseType.ERROR);
            }
        }
        return response;
    }

    private Response sendUpdates() {
        Response response = new Response();
        {
            try {
                if (stop) {
                    response.setException(new ServerStoppedException());
                    response.setResponseType(ResponseType.SUCCESS);
                } else {
                    response.setNotification(notification);
                    response.setResponseType(ResponseType.SUCCESS);
                    notification = new NotificationResponse();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                response.setException(new Exception(ex.getMessage()));
                response.setResponseType(ResponseType.ERROR);
            }
        }
        return response;
    }

    private Response getInvoicesByDate(Request request) {
        Response response = new Response();
        {
            try {
                String date = (String)request.getArgument();
                List<Invoice> invoices = Controller.getInstance().getInvoicesByDate(date);
                response.setResponseType(ResponseType.SUCCESS);
                response.setResult(invoices);
            } catch (Exception ex) {
                ex.printStackTrace();
                response.setException(new Exception(ex.getMessage()));
                response.setResponseType(ResponseType.ERROR);
            }
        }
        return response;
    }

    void notifyNewMedicine(Medicine medicine) {
        notification.setNewMedicine(medicine);
    }

    void notifyNewInvoice(Invoice invoice) {
        notification.setNewInvoice(invoice);
    }

    void notifyChangedInvoice(Invoice invoice) {
        notification.setChangedInvoice(invoice);
    }

    void notifyChangedMedicine(Medicine medicine) {
        Employee emp = this.employee;
        notification.setChangedMedicine(medicine);
    }

    void notifyDeletedMedicine(Medicine medicine) {
        notification.setDeletedMedicine(medicine);
    }
}
