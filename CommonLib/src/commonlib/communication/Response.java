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
public class Response implements Serializable {

    private ResponseType responseType;
    private Object result;
    private Exception exception;
    private NotificationResponse notification;

    public Response() {
    }

    public void setNotification(NotificationResponse notification) {
        this.notification = notification;
    }

    public NotificationResponse getNotification() {
        return notification;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

}
