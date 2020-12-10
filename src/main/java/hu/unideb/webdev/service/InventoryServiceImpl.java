package hu.unideb.webdev.service;

import hu.unideb.webdev.dao.InventoryDao;
import hu.unideb.webdev.exceptions.*;
import hu.unideb.webdev.model.Inventory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{

    private final InventoryDao inventoryDao;

    @Override
    public Collection<Inventory> getAllInventories() {
        return inventoryDao.readAll(Pageable.unpaged());
    }

    @Override
    public Collection<Inventory> getAllInventories(Pageable pageable) {
        return inventoryDao.readAll(pageable);
    }

    @Override
    public void recordInventory(Inventory inventory) throws UnknownFilmException, UnknownStoreException, UnknownLaungeageException, UnknownAddressException, UnknownStaffException {
        inventoryDao.createInventory(inventory);
    }

    @Override
    public void deleteInventory(int inventory) throws UnknownInventoryException {
        inventoryDao.deleteInventory(inventory);
    }

    @Override
    public void updateInventory(Inventory inventory) throws UnknownInventoryException {
        inventoryDao.updateInventory(inventory);
    }
}