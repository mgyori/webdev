package hu.unideb.webdev.service;

import hu.unideb.webdev.TestData;
import hu.unideb.webdev.dao.InventoryDao;
import hu.unideb.webdev.dao.InventoryRepository;
import hu.unideb.webdev.dao.entity.InventoryEntity;
import hu.unideb.webdev.exceptions.*;
import hu.unideb.webdev.model.Inventory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceImplTest {

    @Spy
    @InjectMocks
    private InventoryServiceImpl inventoryService;

    @Mock
    private InventoryDao inventoryDao;

    @Mock
    private InventoryRepository inventoryRepository;

    @Test
    public void testCreateInventory() throws UnknownStoreException, UnknownStaffException, UnknownFilmException, UnknownLaungeageException, UnknownAddressException {
        inventoryService.recordInventory(TestData.getInventory());
        verify(inventoryDao, times(1)).createInventory(TestData.getInventory());
    }

    @Test
    public void testDeleteInventory() throws UnknownInventoryException {
        inventoryService.deleteInventory(1);
        verify(inventoryDao, times(1)).deleteInventory(1);
    }

    @Test
    public void testUpdateInventory() throws UnknownInventoryException {
        inventoryService.updateInventory(TestData.getInventory());
        verify(inventoryDao, times(1)).updateInventory(TestData.getInventory());
    }

    @Test
    public void testRead() {
        when(inventoryDao.readAll(Pageable.unpaged())).thenReturn(Arrays.asList(TestData.getInventory()));
        assertThat(Arrays.asList(TestData.getInventory()), is(inventoryService.getAllInventories()));
        assertThat(Arrays.asList(TestData.getInventory()), is(inventoryService.getAllInventories(Pageable.unpaged())));
    }
}
