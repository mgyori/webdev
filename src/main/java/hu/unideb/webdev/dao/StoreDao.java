package hu.unideb.webdev.dao;

import hu.unideb.webdev.dao.entity.StoreEntity;
import hu.unideb.webdev.exceptions.UnknownAddressException;
import hu.unideb.webdev.exceptions.UnknownStaffException;
import hu.unideb.webdev.exceptions.UnknownStoreException;
import hu.unideb.webdev.model.Store;

import java.util.Collection;

public interface StoreDao {
    StoreEntity createStore(Store staff) throws UnknownAddressException, UnknownStaffException, UnknownStoreException;
    Collection<Store> readAll();
    StoreEntity findByStore(Store store);

    void updateStore(Store staff) throws UnknownAddressException, UnknownStaffException, UnknownStoreException;
    void deleteStore(int id) throws UnknownStoreException;
}
