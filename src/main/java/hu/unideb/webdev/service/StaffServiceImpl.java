package hu.unideb.webdev.service;

import hu.unideb.webdev.dao.StaffDao;
import hu.unideb.webdev.exceptions.UnknownAddressException;
import hu.unideb.webdev.exceptions.UnknownStaffException;
import hu.unideb.webdev.exceptions.UnknownStoreException;
import hu.unideb.webdev.model.Staff;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {
    private final StaffDao staffDao;

    @Override
    public Collection<Staff> getAllStaff() {
        return staffDao.readAll();
    }

    @Override
    public void recordStaff(Staff staff) throws UnknownAddressException, UnknownStoreException {
        staffDao.createStaff(staff);
    }

    @Override
    public void updateStaff(Staff staff) throws UnknownAddressException, UnknownStaffException, UnknownStoreException {
        staffDao.updateStaff(staff);
    }

    @Override
    public void deleteStaff(int id) throws UnknownStaffException {
        staffDao.deleteStaff(id);
    }
}
