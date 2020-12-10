package hu.unideb.webdev.dao;

import hu.unideb.webdev.controller.dto.StaffDto;
import hu.unideb.webdev.dao.converter.SpecialFeaturesList;
import hu.unideb.webdev.dao.entity.AddressEntity;
import hu.unideb.webdev.dao.entity.FilmEntity;
import hu.unideb.webdev.dao.entity.StaffEntity;
import hu.unideb.webdev.dao.entity.StoreEntity;
import hu.unideb.webdev.dao.enums.Rating;
import hu.unideb.webdev.exceptions.UnknownAddressException;
import hu.unideb.webdev.exceptions.UnknownFilmException;
import hu.unideb.webdev.exceptions.UnknownStaffException;
import hu.unideb.webdev.exceptions.UnknownStoreException;
import hu.unideb.webdev.model.Staff;
import hu.unideb.webdev.model.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreDaoImpl implements StoreDao {
    private final StoreRepository storeRepository;
    private final AddressDao addressDao;
    private final StaffDao staffDao;

    @Override
    public StoreEntity createStore(Store store) throws UnknownAddressException, UnknownStaffException, UnknownStoreException {
        AddressEntity adr = addressDao.findByAddress(store.getAddress());
        if (adr == null) {
            try {
                adr = addressDao.createAddress(store.getAddress());
            } catch (Exception e) {
                throw new UnknownAddressException("Address not found!");
            }
        }

        boolean isNew = false;
        StaffEntity staff = staffDao.findByStaff(store.getStaff());
        if (staff == null) {
            try {
                staff = staffDao.createStaff(store.getStaff());
                isNew = true;
            } catch (Exception e) {
                throw new UnknownStaffException("Staff not found!");
            }
        }

        StoreEntity ent = StoreEntity.builder()
                .address(adr)
                .staff(staff)
                .build();
        StoreEntity ret = storeRepository.save(ent);

        if (isNew) {
            Staff st = Converter.staffEntityToModel(staff, Converter.storeEntityToModel(staff.getStore()));
            st.setStore(Converter.storeEntityToModel(ret));
            staffDao.updateStaff(st);
        }

        return ret;
    }

    @Override
    public Collection<Store> readAll() {
        return StreamSupport.stream(storeRepository.findAll().spliterator(),false)
                .map(entity -> Converter.storeEntityToModel(entity))
                .collect(Collectors.toList());
    }

    @Override
    public void updateStore(Store store) throws UnknownAddressException, UnknownStoreException, UnknownStaffException {
        AddressEntity adr = addressDao.findByAddress(store.getAddress());
        if (adr == null) {
            try {
                adr = addressDao.createAddress(store.getAddress());
            } catch (Exception e) {
                throw new UnknownAddressException("Address not found!");
            }
        }

        boolean isNew = false;
        StaffEntity staff = staffDao.findByStaff(store.getStaff());
        if (staff == null) {
            try {
                staff = staffDao.createStaff(store.getStaff());
                isNew = true;
            } catch (Exception e) {
                throw new UnknownStaffException("Staff not found!");
            }
        }

        AddressEntity finalAdr = adr;
        StaffEntity finalStaff = staff;
        Optional<StoreEntity> ent = storeRepository.findById(store.getId()).map(rec -> {
            rec.setAddress(finalAdr);
            rec.setStaff(finalStaff);
            return rec;
        });
        if(!ent.isPresent())
            throw new UnknownStoreException(String.format("Store Entity Not Found %d", store.getId()));
        StoreEntity ret = storeRepository.save(ent.get());

        if (isNew) {
            Staff st = Converter.staffEntityToModel(staff, Converter.storeEntityToModel(staff.getStore()));
            try {
                if (ret != null)
                    st.setStore(Converter.storeEntityToModel(ret));
                staffDao.updateStaff(st);
            } catch (Exception e) {

            }
        }
    }

    @Override
    public StoreEntity findByStore(Store store) {
        Optional<StoreEntity> storeEntity = StreamSupport.stream(storeRepository.findAll().spliterator(),false).filter(
                entity ->{
                    return store.getAddress().getAddress().equals(entity.getAddress().getAddress()) &&
                            //store.getAddress().getAddress2().equals(entity.getAddress().getAddress2()) &&
                            store.getAddress().getCity().equals(entity.getAddress().getCity()) &&
                            store.getAddress().getDistrict().equals(entity.getAddress().getDistrict()) &&
                            store.getStaff().getFirst_name().equals(entity.getStaff().getFirst_name()) &&
                            store.getStaff().getLast_name().equals(entity.getStaff().getLast_name()) &&
                            store.getStaff().getUsername().equals(entity.getStaff().getUsername()) &&
                            store.getStaff().getPassword().equals(entity.getStaff().getPassword()) &&
                            store.getStaff().getEmail().equals(entity.getStaff().getEmail());
                }
        ).findAny();
        if(!storeEntity.isPresent())
            return null;
        return storeEntity.get();
    }

    @Override
    public void deleteStore(int id) throws UnknownStoreException {
        Optional<StoreEntity> filmEntity = storeRepository.findById(id);
        if(!filmEntity.isPresent()){
            throw new UnknownStoreException(String.format("Store Entity Not Found %s",id));
        }
        storeRepository.delete(filmEntity.get());
    }
}
