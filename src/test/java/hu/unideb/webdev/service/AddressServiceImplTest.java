package hu.unideb.webdev.service;

import hu.unideb.webdev.TestData;
import hu.unideb.webdev.dao.AddressDao;
import hu.unideb.webdev.exceptions.UnknownAddressException;
import hu.unideb.webdev.exceptions.UnknownCountryException;
import hu.unideb.webdev.exceptions.UnknownFilmException;
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
class AddressServiceImplTest {

    @InjectMocks
    private AddressServiceImpl service;
    @Mock
    private AddressDao dao;

    @Test
    public void testRecordAddress() throws UnknownCountryException {
        service.recordAddress(TestData.getAddress());
        verify(dao,times(1)).createAddress(TestData.getAddress());
    }

    @Test
    void testRecordAddressWithUnknonwCountry() throws UnknownCountryException {
        doThrow(UnknownCountryException.class).when(dao).createAddress(any());
        assertThrows(UnknownCountryException.class, ()->{
            service.recordAddress(TestData.getAddress());
        });
    }

    @Test
    void testReadAllAddresses(){
        when(dao.readAll()).thenReturn(getDefaultAddresses());
        Collection<Address> actual = service.getAllAddress();
        assertThat(getDefaultAddresses(), is(actual));
    }

    @Test
    void testReadAddressesFromUknonwCity(){
        when(dao.readAll()).thenReturn(getDefaultAddresses());
        final String unknonwCityName = "UnknonwCity";
        Collection<Address> actual = service.getAddressInCity(unknonwCityName);
        assertThat(Collections.emptyList(), is(actual));
    }

    @Test
    void testDelete() throws UnknownAddressException {
        service.deleteAddress(TestData.getAddress());
        verify(dao, times(1)).deleteAddress(TestData.getAddress());
    }


    private Collection<Address> getDefaultAddresses(){
        return Arrays.asList(TestData.getAddress());
    }
}