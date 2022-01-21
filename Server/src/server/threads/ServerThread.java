/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.threads;

import commonlib.domain.Employee;
import commonlib.domain.Invoice;
import commonlib.domain.Medicine;
import commonlib.domain.Operation;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.constant.MyServerConstants;
import server.listener.LoggingListener;

/**
 *
 * @author ANA
 */
public class ServerThread extends Thread {

    private ServerSocket serverSocket;
    private List<HandleClientThread> clients;
    private LoggingListener loggingListener;

    public ServerThread() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream(MyServerConstants.SERVER_CONFIG_FILE_PATH));
        String port = properties.getProperty(MyServerConstants.SERVER_CONFIG_PORT);
        serverSocket = new ServerSocket(Integer.parseInt(port));
        clients = new ArrayList<>();
    }

    @Override
    public void run() {
        while (!serverSocket.isClosed()) {
            try {
                System.out.println("Waiting for client to connect.");
                Socket socket = serverSocket.accept();
                HandleClientThread thread = new HandleClientThread(socket, this);
                thread.start();
                clients.add(thread);
                System.out.println("Client connected.");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        stopAllThreads();
    }

    public void setLoggingListener(LoggingListener loggingListener) {
        this.loggingListener = loggingListener;
    }

    public synchronized void stopAllThreads() {
        while (clients.size() > 0) {
            try {
                if (clients.get(0) != null) {
                    clients.get(0).serverStopped();
                    wait();
                }
                clients.remove(0);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<Employee> getLoggedInEmployees() {
        List<Employee> employees = new LinkedList<>();
        for (HandleClientThread client : clients) {
            if (client != null && client.getEmployee() != null) {
                employees.add(client.getEmployee());
            }
        }
        return employees;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    synchronized void logoutHappened(HandleClientThread aThis) {
        try {
            if (loggingListener != null) {
                loggingListener.logoutHappened(aThis.getEmployee());
            }
            aThis.getSocket().close();
            clients.remove(aThis);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void loginHappened(Employee employee) {
        try {
            if (loggingListener != null) {
                loggingListener.loginHappened(employee);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void notifyNewMedicine(HandleClientThread aThis, Medicine medicine) {
        for (HandleClientThread client : clients) {
            if (client != aThis) {
                client.notifyNewMedicine(medicine);
            }
        }
    }
    
    void notifyNewOperation(HandleClientThread aThis, Operation operation) {
        for (HandleClientThread client : clients) {
            if (client != aThis) {
                client.notifyNewOperation(operation);
            }
        }
    }

    void notifyNewInvoice(HandleClientThread aThis, Invoice invoice) {
        for (HandleClientThread client : clients) {
            if (client != aThis) {
                client.notifyNewInvoice(invoice);
            }
        }
    }

    void notifyChangedInvoice(HandleClientThread aThis, Invoice invoice) {
        for (HandleClientThread client : clients) {
            client.notifyChangedInvoice(invoice);
        }
    }
    
    void notifyChangedMedicine(HandleClientThread aThis, Medicine medicine) {
        for (HandleClientThread client : clients) {
            if (client != aThis) {
                client.notifyChangedMedicine(medicine);
            }
        }
    }
    
    void notifyChangedOperation(HandleClientThread aThis, Operation operation) {
        for (HandleClientThread client : clients) {
            if (client != aThis) {
                client.notifyChangedOperation(operation);
            }
        }
    }
    
    void notifyDeletedMedicine(HandleClientThread aThis, Medicine medicine) {
        for (HandleClientThread client : clients) {
            if (client != aThis) {
                client.notifyDeletedMedicine(medicine);
            }
        }
    }
    
    void notifyDeletedOperation(HandleClientThread aThis, Operation operation) {
        for (HandleClientThread client : clients) {
            if (client != aThis) {
                client.notifyDeletedOperation(operation);
            }
        }
    }

}
