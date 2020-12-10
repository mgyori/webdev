package hu.unideb.webdev.dao;

import hu.unideb.webdev.controller.dto.AddressDto;
import hu.unideb.webdev.dao.entity.AddressEntity;
import hu.unideb.webdev.dao.entity.InventoryEntity;
import hu.unideb.webdev.dao.entity.StaffEntity;
import hu.unideb.webdev.dao.entity.StoreEntity;
import hu.unideb.webdev.exceptions.UnknownAddressException;
import hu.unideb.webdev.exceptions.UnknownStaffException;
import hu.unideb.webdev.exceptions.UnknownStoreException;
import hu.unideb.webdev.model.Address;
import hu.unideb.webdev.model.Staff;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class StaffDaoImpl implements StaffDao {
    private final StaffRepository staffRepository;
    private final AddressDao addressDao;
    private final StoreRepository storeRepository;
    //private final StoreDao storeDao;

    @Override
    public StaffEntity createStaff(Staff staff) throws UnknownAddressException, UnknownStoreException {
        AddressEntity adr = addressDao.findByAddress(staff.getAddress());
        if (adr == null) {
            try {
                adr = addressDao.createAddress(staff.getAddress());
            } catch (Exception e) {
                throw new UnknownAddressException("Address not found!");
            }
        }

        StoreEntity store = storeRepository.findAll().iterator().next();
        /*
        if (staff.getStore() != null) {
            store = storeDao.findByStore(staff.getStore());
            if (store == null) {
                try {
                    store = storeDao.createStore(staff.getStore());
                } catch (Exception e) {
                    throw new UnknownStoreException("Store not found!");
                }
            }
        } else {
            store = storeRepository.findAll().iterator().next();
        }*/

        StaffEntity staffEntity = StaffEntity.builder()
                .active(1)
                .last_name(staff.getLast_name())
                .first_name(staff.getFirst_name())
                .email(staff.getEmail())
                .username(staff.getUsername())
                .password(staff.getPassword())
                .address(adr)
                .store(store)
                .build();

        try {
            return staffRepository.save(staffEntity);
        }
        catch(Exception e){
            log.error(e.getMessage());
        }

        return null;
    }

    @Override
    public Collection<Staff> readAll() {
        return StreamSupport.stream(staffRepository.findAll().spliterator(),false)
                .map(entity -> Converter.staffEntityToModel(entity, entity.getStore() != null? Converter.storeEntityToModel(entity.getStore()) : null))
                .collect(Collectors.toList());
    }

    @Override
    public StaffEntity findByStaff(Staff staff) {
        Optional<StaffEntity> staffEntity = StreamSupport.stream(staffRepository.findAll().spliterator(),false).filter(
                entity ->{
                    return staff.getFirst_name().equals(entity.getFirst_name()) &&
                            staff.getLast_name().equals(entity.getLast_name()) &&
                            staff.getEmail().equals(entity.getEmail()) &&
                            staff.getUsername().equals(entity.getUsername()) &&
                            staff.getPassword().equals(entity.getPassword());
                }
        ).findAny();
        if(!staffEntity.isPresent())
            return null;
        return staffEntity.get();
    }

    @Override
    public void updateStaff(Staff staff) throws UnknownAddressException, UnknownStaffException, UnknownStoreException {
        AddressEntity adr = addressDao.findByAddress(staff.getAddress());
        if (adr == null) {
            try {
                adr = addressDao.createAddress(staff.getAddress());
            } catch (Exception e) {
                throw new UnknownAddressException("Address not found!");
            }
        }

        /*StoreEntity store = storeDao.findByStore(staff.getStore());
        if (store == null) {
            try {
                store = storeDao.createStore(staff.getStore());
            } catch (Exception e) {
                throw new UnknownStoreException("Store not found!");
            }
        }*/

        AddressEntity finalAdr = adr;
        //StoreEntity finalStore = store;
        Optional<StaffEntity> ent = staffRepository.findById(staff.getId()).map(rec -> {
            rec.setAddress(finalAdr);
            rec.setFirst_name(staff.getFirst_name());
            rec.setLast_name(staff.getLast_name());
            rec.setEmail(staff.getEmail());
            rec.setUsername(staff.getUsername());
            rec.setPassword(staff.getPassword());
            //rec.setStore(finalStore);
            return rec;
        });
        if(!ent.isPresent())
            throw new UnknownStaffException(String.format("Staff Entity Not Found %d", staff.getId()));
        staffRepository.save(ent.get());
    }

    @Override
    public void deleteStaff(int id) throws UnknownStaffException {
        Optional<StaffEntity> staffEntity = staffRepository.findById(id);
        if (!staffEntity.isPresent())
            throw new UnknownStaffException();
        staffRepository.delete(staffEntity.get());
    }
}
