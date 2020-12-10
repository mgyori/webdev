package hu.unideb.webdev.service;

import hu.unideb.webdev.exceptions.*;
import hu.unideb.webdev.model.Address;
import hu.unideb.webdev.model.Inventory;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface InventoryService {
    Collection<Inventory> getAllInventories();
    Collection<Inventory> getAllInventories(Pageable pageable);

    void recordInventory(Inventory inventory) throws UnknownFilmException, UnknownStoreException, UnknownLaungeageException, UnknownAddressException, UnknownStaffException;
    void deleteInventory(int inventory) throws UnknownInventoryException;
    void updateInventory(Inventory inventory) throws UnknownInventoryException;
}
