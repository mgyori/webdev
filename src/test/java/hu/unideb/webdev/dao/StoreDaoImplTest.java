package hu.unideb.webdev.dao;

import hu.unideb.webdev.TestData;
import hu.unideb.webdev.controller.dto.AddressRecordResquestDto;
import hu.unideb.webdev.controller.dto.StoreDto;
import hu.unideb.webdev.dao.converter.SpecialFeaturesList;
import hu.unideb.webdev.dao.entity.CityEntity;
import hu.unideb.webdev.dao.entity.FilmEntity;
import hu.unideb.webdev.dao.entity.LangueageEntity;
import hu.unideb.webdev.dao.enums.Rating;
import hu.unideb.webdev.exceptions.*;
import hu.unideb.webdev.model.Film;
import hu.unideb.webdev.model.Staff;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StoreDaoImplTest {
    @Spy
    @InjectMocks
    private StoreDaoImpl dao;

    @Mock
    private StaffRepository staffRepository;

    @Mock
    private LangueageRepository langueageRepository;

    @Mock
    private AddressDao addressDao;

    @InjectMocks
    private StaffDaoImpl staffDao;

    @Mock
    private StaffDao staffDao2;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private StoreRepository storeRepository;

    /*
    @Test
    void testCreateStore() throws UnknownAddressException, UnknownStoreException, UnknownStaffException {
        when(storeRepository.findAll()).thenReturn(Arrays.asList(TestData.getStoreEntity()));
        dao.createStore(TestData.getStore());
        verify(storeRepository,times(1)).save(any());
    }

    @Test
    void testUpdateStore() throws UnknownAddressException, UnknownStaffException, UnknownStoreException {
        when(storeRepository.findById(anyInt())).thenReturn(Optional.of(TestData.getStoreEntity()));
        //dao.updateStore(TestData.getStore());
    }
    */

    @Test
    void testDeleteStore() throws UnknownStoreException {
        assertThrows(UnknownStoreException.class,() -> {
            dao.deleteStore(1);
        });
        when(storeRepository.findById(anyInt())).thenReturn(Optional.of(TestData.getStoreEntity()));
        dao.deleteStore(1);
    }

    @Test
    void testReadALl() {
        when(storeRepository.findAll()).thenReturn(Arrays.asList(TestData.getStoreEntity()));
        assertEquals(dao.readAll(), Arrays.asList(TestData.getStore()));
    }

    /*
    @Test
    void testFindByStore() {
        //assertThrows(UnknownStaffException.class,() -> {
        //    dao.findByStaff(TestData.getStaff());
        //});
        when(storeRepository.findAll()).thenReturn(Arrays.asList(TestData.getStoreEntity()));
        //assertEquals(TestData.getStoreEntity(), dao.findByStore(TestData.getStore()));
    }*/
}
