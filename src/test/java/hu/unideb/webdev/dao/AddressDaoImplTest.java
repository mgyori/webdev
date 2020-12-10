package hu.unideb.webdev.dao;

import hu.unideb.webdev.TestData;
import hu.unideb.webdev.dao.entity.AddressEntity;
import hu.unideb.webdev.dao.entity.CityEntity;
import hu.unideb.webdev.dao.entity.CountryEntity;
import hu.unideb.webdev.exceptions.UnknownAddressException;
import hu.unideb.webdev.exceptions.UnknownCountryException;
import hu.unideb.webdev.exceptions.UnknownFilmException;
import hu.unideb.webdev.model.Address;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class AddressDaoImplTest {

    @Spy
    @InjectMocks
    private AddressDaoImpl dao;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private CountryRepository countryRepository;


    @Test
    void testCreateAddress() throws UnknownCountryException {

//        when(dao.queryCity(any(), any())).thenReturn(CityEntity.builder().build());
        doReturn(CityEntity.builder().name("Debrecen").build())
                .when(dao).queryCity(any(),any());

        dao.createAddress(TestData.getAddress());

        verify(addressRepository,times(1)).save(any());
    }

    @Test
    void testReadAll() {
        when(addressRepository.findAll()).thenReturn(Arrays.asList(TestData.getAddressEntity()));
        assertEquals(dao.readAll(), Arrays.asList(TestData.getAddress()));
    }
}