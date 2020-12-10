package hu.unideb.webdev.dao;

import hu.unideb.webdev.TestData;
import hu.unideb.webdev.dao.converter.SpecialFeaturesList;
import hu.unideb.webdev.dao.entity.CityEntity;
import hu.unideb.webdev.dao.entity.FilmEntity;
import hu.unideb.webdev.dao.entity.LangueageEntity;
import hu.unideb.webdev.dao.enums.Rating;
import hu.unideb.webdev.exceptions.UnknownCountryException;
import hu.unideb.webdev.exceptions.UnknownFilmException;
import hu.unideb.webdev.exceptions.UnknownInventoryException;
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
public class FilmDaoImplTest {
    @Spy
    @InjectMocks
    private FilmDaoImpl dao;

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private LangueageRepository langueageRepository;

    @Test
    void testCreateFilm() throws UnknownCountryException {
        dao.createFilm(TestData.getFilm());
        verify(filmRepository,times(1)).save(any());
    }

    @Test
    void testUpdateFilm() throws UnknownFilmException {
        when(filmRepository.findById(anyInt())).thenReturn(Optional.of(TestData.getFilmEntity()));
        dao.updateFilm(TestData.getFilm());
    }

    @Test
    void testDeleteFilm() throws UnknownFilmException {
        assertThrows(UnknownFilmException.class,() -> {
            dao.deleteFilm(1);
        });
        when(filmRepository.findById(anyInt())).thenReturn(Optional.of(TestData.getFilmEntity()));
        dao.deleteFilm(1);
    }

    @Test
    void testReadALl() {
        when(filmRepository.findAll()).thenReturn(Arrays.asList(TestData.getFilmEntity()));
        assertEquals(dao.readAll(), Arrays.asList(TestData.getFilm()));
    }

    @Test
    void testFindByFilm() throws UnknownFilmException {
        when(filmRepository.findAll()).thenReturn(Arrays.asList(TestData.getFilmEntity()));
        assertThrows(UnknownFilmException.class,() -> {
            dao.findByFilm(TestData.getFilm());
        });
    }
}
