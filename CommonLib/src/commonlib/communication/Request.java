/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commonlib.communication;

import java.io.Serializable;

/**
 *
 * @author ANA
 */
public class Request implements Serializable{
    private int operation; //tip operacije
    private Object argument;
    

    public Request(int operation, Object argument) {
        this.operation = operation;
        this.argument = argument;
    }

    public Object getArgument() {
        return argument;
    }

    public int getOperation() {
        return operation;
    }
    
}
