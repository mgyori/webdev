package hu.unideb.webdev.dao;

import hu.unideb.webdev.TestData;
import hu.unideb.webdev.controller.dto.StoreDto;
import hu.unideb.webdev.dao.converter.SpecialFeaturesList;
import hu.unideb.webdev.dao.entity.*;
import hu.unideb.webdev.dao.enums.Rating;
import hu.unideb.webdev.exceptions.*;
import hu.unideb.webdev.model.*;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.Year;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InventoryDaoImplTest {
    @Spy
    @InjectMocks
    private InventoryDaoImpl inventoryDao;

    @Spy
    @InjectMocks
    private AddressDaoImpl addressDao;

    @Spy
    @InjectMocks
    private FilmDaoImpl filmDao;

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private LangueageRepository langueageRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private FilmDao filmDao2;

    @Mock
    private StoreDao storeDao;

    @Mock
    private StoreRepository storeRepository;

    @Test
    void testCreateInventory() throws UnknownFilmException, UnknownStoreException, UnknownCountryException, UnknownLaungeageException, UnknownAddressException, UnknownStaffException {
        assertThrows(UnknownFilmException.class,() -> {
            filmDao.findByFilm(TestData.getFilm());
        });
        inventoryDao.createInventory(TestData.getInventory());
        verify(inventoryRepository,times(1)).save(any());
    }

    @Test
    void testDeleteInventory() throws UnknownStoreException, UnknownStaffException, UnknownFilmException, UnknownLaungeageException, UnknownAddressException, UnknownInventoryException {
        when(inventoryRepository.findById(anyInt())).thenReturn(Optional.of(TestData.getInventoryEntity()));
        inventoryDao.deleteInventory(1);
        verify(inventoryDao, times(1)).deleteInventory(1);
        assertEquals(0, inventoryRepository.count());

        when(inventoryRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(UnknownInventoryException.class,() -> {
            inventoryDao.deleteInventory(1);
        });
    }

    @Test
    void testReadInventories() {
        when(inventoryRepository.findAll(Pageable.unpaged())).thenReturn(TestData.getInventoryEntities());
        assertEquals(inventoryDao.readAll(Pageable.unpaged()), TestData.getInventories());
    }

    @Test
    void testUpdateInventory() throws UnknownInventoryException {
        assertThrows(UnknownInventoryException.class,() -> {
            inventoryDao.updateInventory(TestData.getInventory());
        });
        when(storeRepository.findById(anyInt())).thenReturn(Optional.of(TestData.getStoreEntity()));
        when(filmRepository.findById(anyInt())).thenReturn(Optional.of(TestData.getFilmEntity()));
        when(inventoryRepository.findById(anyInt())).thenReturn(Optional.of(TestData.getInventoryEntity()));
        inventoryDao.updateInventory(TestData.getInventory());
    }
}
