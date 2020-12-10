package hu.unideb.webdev.dao;

import hu.unideb.webdev.dao.entity.InventoryEntity;
import hu.unideb.webdev.exceptions.*;
import hu.unideb.webdev.model.Inventory;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface InventoryDao {
    InventoryEntity createInventory(Inventory inventory) throws UnknownFilmException, UnknownStoreException, UnknownLaungeageException, UnknownAddressException, UnknownStaffException;
    Collection<Inventory> readAll(Pageable page);

    void deleteInventory(int id) throws UnknownInventoryException;
    void updateInventory(Inventory inventory) throws UnknownInventoryException;
}
