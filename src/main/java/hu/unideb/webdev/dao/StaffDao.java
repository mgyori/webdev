package hu.unideb.webdev.dao;

import hu.unideb.webdev.dao.entity.StaffEntity;
import hu.unideb.webdev.exceptions.UnknownAddressException;
import hu.unideb.webdev.exceptions.UnknownStaffException;
import hu.unideb.webdev.exceptions.UnknownStoreException;
import hu.unideb.webdev.model.Staff;

import java.util.Collection;

public interface StaffDao {
    StaffEntity createStaff(Staff staff) throws UnknownAddressException, UnknownStoreException;
    Collection<Staff> readAll();
    StaffEntity findByStaff(Staff staff);

    void updateStaff(Staff staff) throws UnknownAddressException, UnknownStaffException, UnknownStoreException;
    void deleteStaff(int id) throws UnknownStaffException;
}
