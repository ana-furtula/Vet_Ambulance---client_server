/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import client.view.form.FormLogin;
import java.io.IOException;
import java.net.Socket;
import client.communication.Communication;
import client.view.controller.Controller;
import commonlib.communication.NotificationResponse;
import commonlib.communication.Operations;
import commonlib.communication.Receiver;
import commonlib.communication.Request;
import commonlib.communication.Response;
import commonlib.communication.Sender;
import commonlib.domain.Invoice;
import commonlib.domain.Medicine;
import commonlib.domain.ModelElement;
import commonlib.domain.Operation;
import commonlib.exception.ServerStoppedException;
import static java.lang.Thread.sleep;
import java.util.List;

/**
 *
 * @author ANA
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.connect();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void connect() throws IOException {
        Socket socket = new Socket("127.0.0.1", 9000);
        System.out.println("Connected with server");
        FormLogin login = new FormLogin();
        login.setVisible(true);
        Receiver receiver = new Receiver(socket);
        Sender sender = new Sender(socket);
        ThreadListener tl = new ThreadListener(receiver, sender);
        Communication.getInstance().setSender(sender);
        Communication.getInstance().setReceiver(receiver);
    }

}

class ThreadListener extends Thread {

    private Receiver receiver;
    private Sender sender;

    public ThreadListener(Receiver receiver, Sender sender) {
        this.receiver = receiver;
        this.sender = sender;
        start();
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                sleep(3000);
                Request request = new Request(Operations.UPDATES, null);
                sender.send(request);
                Response response = (Response) receiver.receive();
                handleResponse(response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void handleResponse(Response response) {
        try {
            if (response.getException() != null && response.getException() instanceof ServerStoppedException) {
                Request request = new Request(Operations.SERVER_STOPPED_ACK, null);
                sender.send(request);
                Controller.getInstance().finish();
                this.interrupt();
            } else {
                NotificationResponse notification = (NotificationResponse) response.getNotification();
                for (ModelElement newElement : notification.getNewElements()) {
                    if (newElement instanceof Medicine) {
                        Controller.getInstance().notifyNewMedicine((Medicine) newElement);
                        continue;
                    }
                    if (newElement instanceof Invoice) {
                        Controller.getInstance().notifyNewInvoice((Invoice) newElement);
                        continue;
                    }
                    if (newElement instanceof Operation) {
                        Controller.getInstance().notifyNewOperation((Operation) newElement);
                    }
                }
                for (ModelElement changedElement : notification.getChangedElements()) {
                    if (changedElement instanceof Medicine) {
                        Controller.getInstance().notifyChangedMedicine((Medicine) changedElement);
                        continue;
                    }
                    if (changedElement instanceof Invoice) {
                        Controller.getInstance().notifyChangedInvoice((Invoice) changedElement);
                        continue;
                    }
                    if (changedElement instanceof Operation) {
                        Controller.getInstance().notifyChangedOperation((Operation) changedElement);
                    }
                }
                for (ModelElement deletedElement : notification.getDeletedElements()) {
                    if (deletedElement instanceof Medicine) {
                        Controller.getInstance().notifyDeletedMedicine((Medicine) deletedElement);
                        continue;
                    }
                    if (deletedElement instanceof Operation) {
                        Controller.getInstance().notifyDeletedOperation((Operation) deletedElement);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
