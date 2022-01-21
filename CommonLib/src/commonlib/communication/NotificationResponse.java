/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commonlib.communication;

import commonlib.domain.Invoice;
import commonlib.domain.Medicine;
import commonlib.domain.ModelElement;
import commonlib.domain.Operation;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author ANA
 */
public class NotificationResponse implements Serializable {

    private List<ModelElement> newElements;
    private List<ModelElement> changedElements;
    private List<ModelElement> deletedElements;

    public NotificationResponse() {
        newElements = new LinkedList<>();
        changedElements = new LinkedList<>();
        deletedElements = new LinkedList<>();
    }

    public void addNewElement(ModelElement newElement) {
        this.newElements.add(newElement);
    }

    public void addChangedElement(ModelElement newElement) {
        this.changedElements.add(newElement);
    }

    public void addDeletedElement(ModelElement newElement) {
        this.deletedElements.add(newElement);
    }

    public List<ModelElement> getNewElements() {
        return newElements;
    }

    public List<ModelElement> getChangedElements() {
        return changedElements;
    }

    public List<ModelElement> getDeletedElements() {
        return deletedElements;
    }
    
}
