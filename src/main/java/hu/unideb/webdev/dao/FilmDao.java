package hu.unideb.webdev.dao;

import hu.unideb.webdev.dao.entity.FilmEntity;
import hu.unideb.webdev.exceptions.UnknownAddressException;
import hu.unideb.webdev.exceptions.UnknownFilmException;
import hu.unideb.webdev.exceptions.UnknownLaungeageException;
import hu.unideb.webdev.model.Film;
import hu.unideb.webdev.model.Staff;

import java.util.Collection;

public interface FilmDao {
    FilmEntity createFilm(Film film);
    Collection<Film> readAll();
    FilmEntity findByFilm(Film film) throws UnknownFilmException;

    void updateFilm(Film film) throws UnknownFilmException;
    void deleteFilm(int id) throws UnknownFilmException;
}
