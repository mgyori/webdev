package hu.unideb.webdev.service;

import hu.unideb.webdev.exceptions.UnknownAddressException;
import hu.unideb.webdev.exceptions.UnknownStaffException;
import hu.unideb.webdev.exceptions.UnknownStoreException;
import hu.unideb.webdev.model.Inventory;
import hu.unideb.webdev.model.Staff;

import java.util.Collection;

public interface StaffService {
    Collection<Staff> getAllStaff();

    void recordStaff(Staff staff) throws UnknownAddressException, UnknownStoreException;
    void updateStaff(Staff staff) throws UnknownAddressException, UnknownStaffException, UnknownStoreException;
    void deleteStaff(int id) throws UnknownStaffException;
}
