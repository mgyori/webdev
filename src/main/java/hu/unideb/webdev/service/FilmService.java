package hu.unideb.webdev.service;

import hu.unideb.webdev.exceptions.UnknownAddressException;
import hu.unideb.webdev.exceptions.UnknownFilmException;
import hu.unideb.webdev.exceptions.UnknownLaungeageException;
import hu.unideb.webdev.exceptions.UnknownStaffException;
import hu.unideb.webdev.model.Film;

import java.util.Collection;

public interface FilmService {
    Collection<Film> getAllFilm();

    void recordFilm(Film film);
    void updateFilm(Film film) throws UnknownFilmException;
    void deleteFilm(int id) throws UnknownFilmException;
}
