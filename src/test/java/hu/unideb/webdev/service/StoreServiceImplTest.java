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
class StoreServiceImplTest {

    @InjectMocks
    private StoreServiceImpl service;

    @Mock
    private StoreDao dao;

    @Mock
    private StoreRepository storeRepository;

    @Test
    public void testAddStore() throws UnknownAddressException, UnknownStoreException, UnknownStaffException {
        service.recordStore(TestData.getStore());
        verify(dao,times(1)).createStore(TestData.getStore());
    }

    @Test
    void testRead(){
        when(dao.readAll()).thenReturn(Arrays.asList(TestData.getStore()));
        assertThat(Arrays.asList(TestData.getStore()), is(service.getAllStore()));
    }

    @Test
    void testDelete() throws UnknownStoreException {
        service.deleteStore(1);
        verify(dao, times(1)).deleteStore(1);
    }

    @Test
    public void testUpdate() throws UnknownAddressException, UnknownStaffException, UnknownStoreException {
        service.updateStore(TestData.getStore());
        verify(dao, times(1)).updateStore(TestData.getStore());
    }
}