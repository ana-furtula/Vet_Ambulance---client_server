/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commonlib.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ANA
 */
public class Receiver {
    private Socket socket;

    public Receiver(Socket socket) {
        this.socket = socket;
    }
    
    public synchronized Object receive() throws Exception{
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            return in.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error while receiving object: " + ex.getMessage());
        }
    }
}
