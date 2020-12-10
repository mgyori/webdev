package hu.unideb.webdev.dao;

import hu.unideb.webdev.TestData;
import hu.unideb.webdev.controller.dto.AddressRecordResquestDto;
import hu.unideb.webdev.dao.converter.SpecialFeaturesList;
import hu.unideb.webdev.dao.entity.CityEntity;
import hu.unideb.webdev.dao.entity.FilmEntity;
import hu.unideb.webdev.dao.entity.LangueageEntity;
import hu.unideb.webdev.dao.enums.Rating;
import hu.unideb.webdev.exceptions.*;
import hu.unideb.webdev.model.Film;
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
public class StaffDaoImplTest {
    @Spy
    @InjectMocks
    private StaffDaoImpl dao;

    @Mock
    private StaffRepository staffRepository;

    @Mock
    private LangueageRepository langueageRepository;

    @Mock
    private AddressDao addressDao;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private StoreRepository storeRepository;

    @Test
    void testCreateStaff() throws UnknownAddressException, UnknownStoreException {
        when(storeRepository.findAll()).thenReturn(Arrays.asList(TestData.getStoreEntity()));
        dao.createStaff(TestData.getStaff());
        verify(staffRepository,times(1)).save(any());
    }

    @Test
    void testUpdateStaff() throws UnknownAddressException, UnknownStaffException, UnknownStoreException {
        when(staffRepository.findById(anyInt())).thenReturn(Optional.of(TestData.getStaffEntity()));
        dao.updateStaff(TestData.getStaff());
    }

    @Test
    void testDeleteStaff() throws UnknownStaffException {
        assertThrows(UnknownStaffException.class,() -> {
            dao.deleteStaff(1);
        });
        when(staffRepository.findById(anyInt())).thenReturn(Optional.of(TestData.getStaffEntity()));
        dao.deleteStaff(1);
    }

    @Test
    void testReadALl() {
        when(staffRepository.findAll()).thenReturn(Arrays.asList(TestData.getStaffEntity()));
        assertEquals(dao.readAll(), Arrays.asList(TestData.getStaff()));
    }

    @Test
    void testFindByStaff() {
        //assertThrows(UnknownStaffException.class,() -> {
        //    dao.findByStaff(TestData.getStaff());
        //});
        when(staffRepository.findAll()).thenReturn(Arrays.asList(TestData.getStaffEntity()));
        assertEquals(TestData.getStaffEntity(), dao.findByStaff(TestData.getStaff()));
    }
}
