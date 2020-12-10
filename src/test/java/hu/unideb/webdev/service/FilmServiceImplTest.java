package hu.unideb.webdev.service;

import hu.unideb.webdev.TestData;
import hu.unideb.webdev.dao.AddressDao;
import hu.unideb.webdev.dao.FilmDao;
import hu.unideb.webdev.dao.FilmRepository;
import hu.unideb.webdev.exceptions.UnknownCountryException;
import hu.unideb.webdev.exceptions.UnknownFilmException;
import hu.unideb.webdev.exceptions.UnknownInventoryException;
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
class FilmServiceImplTest {

    @InjectMocks
    private FilmServiceImpl service;

    @Mock
    private FilmDao dao;

    @Mock
    private FilmRepository filmRepository;

    @Test
    public void testAddFilm() throws UnknownCountryException {
        service.recordFilm(TestData.getFilm());
        verify(dao,times(1)).createFilm(TestData.getFilm());
    }

    @Test
    void testRead(){
        when(dao.readAll()).thenReturn(Arrays.asList(TestData.getFilm()));
        assertThat(Arrays.asList(TestData.getFilm()), is(service.getAllFilm()));
    }

    @Test
    void testDelete() throws UnknownFilmException {
        service.deleteFilm(1);
        verify(dao, times(1)).deleteFilm(1);
    }

    @Test
    public void testUpdateInventory() throws UnknownInventoryException, UnknownFilmException {
        service.updateFilm(TestData.getFilm());
        verify(dao, times(1)).updateFilm(TestData.getFilm());
    }
}