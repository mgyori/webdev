package hu.unideb.webdev.service;

import hu.unideb.webdev.dao.FilmDao;
import hu.unideb.webdev.exceptions.UnknownFilmException;
import hu.unideb.webdev.exceptions.UnknownLaungeageException;
import hu.unideb.webdev.model.Film;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmDao filmDao;

    @Override
    public Collection<Film> getAllFilm() {
        return filmDao.readAll();
    }

    @Override
    public void recordFilm(Film film) {
        filmDao.createFilm(film);
    }

    @Override
    public void updateFilm(Film film) throws UnknownFilmException {
        filmDao.updateFilm(film);
    }

    @Override
    public void deleteFilm(int id) throws UnknownFilmException {
        filmDao.deleteFilm(id);
    }
}
