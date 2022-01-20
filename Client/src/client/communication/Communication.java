/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.communication;

import commonlib.communication.Operations;
import commonlib.communication.Receiver;
import commonlib.communication.Request;
import commonlib.communication.Response;
import commonlib.communication.Sender;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ANA
 */
public class Communication {

    private static Communication instance;
    private Receiver receiver;
    private Sender sender;

    private Communication() {
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public static Communication getInstance() {
        if (instance == null) {
            instance = new Communication();
        }
        return instance;
    }

    public Response login(Request request) throws Exception {
        try {
            sender.send(request);
            return (Response) receiver.receive();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error in logging user: " + ex.getMessage());
        }
    }

    public void logout() throws Exception {
        try {
            Request request = new Request(Operations.LOGOUT, null);
            sender.send(request);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error in getting logging out: " + ex.getMessage());
        }
    }

    public Response getCurrentUser() throws Exception {
        try {
            Request request = new Request(Operations.GET_CURRENT_USER, null);
            sender.send(request);
            return (Response) receiver.receive();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error in getting current user: " + ex.getMessage());
        }
    }

    public Response changePassword(Request request) throws Exception {
        try {
            sender.send(request);
            return (Response) receiver.receive();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Greška prilikom promjene šifre: " + ex.getMessage());
        }
    }

    public Response getAllMedicines() throws Exception {
        try {
            Request request = new Request(Operations.GET_ALL_MEDICINES, null);
            sender.send(request);
            return (Response) receiver.receive();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Greška prilikom inicijalizacije stranice: " + ex.getMessage());
        }
    }

    public Response deleteMedicine(Request request) throws Exception {
        try {
            sender.send(request);
            return (Response) receiver.receive();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Greška prilikom brisanja lijeka: " + ex.getMessage());
        }
    }

    public Response createMedicine(Request request) throws Exception {
        try {
            sender.send(request);
            return (Response) receiver.receive();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Greška prilikom kreiranja novog lijeka " + ex.getMessage());
        }
    }

    public Response updateMedicine(Request request) throws Exception {
        try {
            sender.send(request);
            return (Response) receiver.receive();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Greška prilikom izmijene lijeka: " + ex.getMessage());
        }
    }

    public Response createOperation(Request request) throws Exception {
        try {
            sender.send(request);
            return (Response) receiver.receive();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Greška prilikom kreiranja nove operacije " + ex.getMessage());
        }
    }

    public Response getOperationById(Request request) throws Exception {
        try {
            sender.send(request);
            return (Response) receiver.receive();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Greška prilikom učitavanja operacije " + ex.getMessage());
        }
    }

    public Response updateOperation(Request request) throws Exception {
        try {
            sender.send(request);
            return (Response) receiver.receive();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Greška prilikom izmijene operacije: " + ex.getMessage());
        }
    }

    public Response deleteOperation(Request request) throws Exception {
        try {
            sender.send(request);
            return (Response) receiver.receive();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Greška prilikom brisanja operacije: " + ex.getMessage());
        }
    }

    public Response getAllClients() throws Exception {
        try {
            Request request = new Request(Operations.GET_ALL_CLIENTS, null);
            sender.send(request);
            return (Response) receiver.receive();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Greška prilikom preuzimanja liste klijenata: " + ex.getMessage());
        }
    }

    public Response getAllOperations() throws Exception {
        try {
            Request request = new Request(Operations.GET_ALL_OPERATIONS, null);
            sender.send(request);
            return (Response) receiver.receive();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Greška prilikom preuzimanja liste operacija: " + ex.getMessage());
        }
    }

    public Response saveInvoice(Request request) throws Exception {
        try {
            sender.send(request);
            return (Response) receiver.receive();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Greška prilikom čuvanja računa: " + ex.getMessage());
        }
    }

    public Response getAllInvoices() throws Exception {
        try {
            Request request = new Request(Operations.GET_ALL_INVOICES, null);
            sender.send(request);
            return (Response) receiver.receive();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Greška prilikom preuzimanja liste računa: " + ex.getMessage());
        }
    }

    public Response updateInvoice(Request request) throws Exception {
        try {
            sender.send(request);
            return (Response) receiver.receive();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Greška prilikom čuvanja računa: " + ex.getMessage());
        }
    }

    public Response getInvoicesByDate(String date) throws Exception {
        try {
            Request request = new Request(Operations.GET_INVOICES_BY_DATE, date);
            sender.send(request);
            return (Response) receiver.receive();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Greška prilikom preuzimanja liste računa: " + ex.getMessage());
        }
    }

}
