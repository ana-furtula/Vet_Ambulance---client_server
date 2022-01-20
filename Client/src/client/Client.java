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
            } else{
                NotificationResponse notification = (NotificationResponse)response.getNotification();
                if(notification.getNewMedicine()!=null){
                    Controller.getInstance().notifyNewMedicine(notification.getNewMedicine());
                }
                if(notification.getChangedInvoice()!=null){
                    Controller.getInstance().notifyChangedInvoice(notification.getChangedInvoice());
                }
                if(notification.getNewInvoice()!=null){
                    Controller.getInstance().notifyNewInvoice(notification.getNewInvoice());
                }
                if(notification.getChangedMedicine()!=null){
                    Controller.getInstance().notifyChangedMedicine(notification.getChangedMedicine());
                }
                if(notification.getDeletedMedicine()!=null){
                    Controller.getInstance().notifyDeletedMedicine(notification.getDeletedMedicine());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
