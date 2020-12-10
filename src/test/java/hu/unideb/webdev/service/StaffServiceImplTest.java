package hu.unideb.webdev.service;

import hu.unideb.webdev.TestData;
import hu.unideb.webdev.dao.*;
import hu.unideb.webdev.exceptions.*;
import hu.unideb.webdev.model.Address;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@ExtendWith(MockitoExtension.class)
class StaffServiceImplTest {

    @InjectMocks
    private StaffServiceImpl service;

    @Mock
    private StaffDao dao;

    @Mock
    private StaffRepository storeRepository;

    @Test
    public void testAddStaff() throws UnknownAddressException, UnknownStoreException {
        service.recordStaff(TestData.getStaff());
        verify(dao,times(1)).createStaff(TestData.getStaff());
    }

    @Test
    void testRead(){
        when(dao.readAll()).thenReturn(Arrays.asList(TestData.getStaff()));
        assertThat(Arrays.asList(TestData.getStaff()), is(service.getAllStaff()));
    }

    @Test
    void testDelete() throws UnknownStaffException {
        service.deleteStaff(1);
        verify(dao, times(1)).deleteStaff(1);
    }

    @Test
    public void testUpdate() throws UnknownAddressException, UnknownStaffException, UnknownStoreException {
        service.updateStaff(TestData.getStaff());
        verify(dao, times(1)).updateStaff(TestData.getStaff());
    }
}