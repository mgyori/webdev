package hu.unideb.webdev.exceptions;

import hu.unideb.webdev.model.Inventory;

public class UnknownInventoryException extends Exception {

    private Inventory inventory;

    public UnknownInventoryException(Inventory inventory) {
        this.inventory = inventory;
    }

    public UnknownInventoryException(String message, Inventory inventory) {
        super(message);
        this.inventory = inventory;
    }

    public UnknownInventoryException(String message) {
        super(message);
    }
}
