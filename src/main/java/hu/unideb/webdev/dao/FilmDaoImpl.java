package hu.unideb.webdev.dao;

import hu.unideb.webdev.dao.converter.SpecialFeaturesList;
import hu.unideb.webdev.dao.entity.*;
import hu.unideb.webdev.dao.enums.Rating;
import hu.unideb.webdev.exceptions.UnknownCountryException;
import hu.unideb.webdev.exceptions.UnknownFilmException;
import hu.unideb.webdev.exceptions.UnknownInventoryException;
import hu.unideb.webdev.exceptions.UnknownLaungeageException;
import hu.unideb.webdev.model.Film;
import hu.unideb.webdev.model.Staff;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Year;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmDaoImpl implements FilmDao {
    private final FilmRepository filmRepository;
    private final LangueageRepository langeageRepository;

    @Override
    public FilmEntity createFilm(Film film) {
        FilmEntity ent = FilmEntity.builder()
                .title(film.getTitle())
                .description(film.getDescription())
                .release_year(Year.parse(film.getRelease_year().toString()))
                .language(queryLangueage(film.getLanguage()))
                .rental_duration((short) film.getRental_duration())
                .rental_rate(film.getRental_rate())
                .length(film.getLength())
                .replacement_cost(film.getReplacement_cost())
                .rating(Rating.valueOf(film.getRating().replace("-", "")))
                .special_features(SpecialFeaturesList.to(film.getSpecial_features()))
                .build();
        return filmRepository.save(ent);
    }

    protected LangueageEntity queryLangueage(String laungeage) {
        Optional<LangueageEntity> langeageEntity = langeageRepository.findByName(laungeage);
        if(!langeageEntity.isPresent()){
            langeageEntity = Optional.ofNullable(LangueageEntity.builder()
                    .name(laungeage)
                    .lastUpdate(new Timestamp((new Date()).getTime()))
                    .build());
            log.info("Recorded new Laungeage: {}", laungeage);
            return langeageRepository.save(langeageEntity.get());
        }
        return langeageEntity.get();
    }

    @Override
    public Collection<Film> readAll() {
        return StreamSupport.stream(filmRepository.findAll().spliterator(),false)
                .map(entity -> Converter.filmEntityToModel(entity))
                .collect(Collectors.toList());
    }

    @Override
    public void updateFilm(Film film) throws UnknownFilmException {
        Optional<FilmEntity> ent = filmRepository.findById(film.getId()).map(rec -> {
            rec.setTitle(film.getTitle());
            rec.setDescription(film.getDescription());
            rec.setRelease_year(Year.parse(film.getRelease_year().toString()));
            rec.setLanguage(queryLangueage(film.getLanguage()));
            rec.setRental_duration((short) film.getRental_duration());
            rec.setRental_rate(film.getRental_rate());
            rec.setLength(film.getLength());
            rec.setReplacement_cost(film.getReplacement_cost());
            rec.setRating(Rating.valueOf(film.getRating().replace("-", "")));
            rec.setSpecial_features(SpecialFeaturesList.to(film.getSpecial_features()));
            return rec;
        });
        if(!ent.isPresent())
            throw new UnknownFilmException(String.format("Film Entity Not Found %d", film.getId()));
        filmRepository.save(ent.get());
    }

    @Override
    public FilmEntity findByFilm(Film film) throws UnknownFilmException {
        Optional<FilmEntity> filmEntity = StreamSupport.stream(filmRepository.findAll().spliterator(),false).filter(
                entity ->{
                    return film.getTitle().equals(entity.getTitle()) &&
                            film.getDescription().equals(entity.getDescription()) &&
                            film.getLanguage().equals(entity.getLanguage()) &&
                            film.getRating().equals(entity.getRating()) &&
                            film.getLength() == entity.getLength()/* &&
                            film.getRelease_year().equals((short)entity.getRelease_year().getValue()) &&
                            film.getReplacement_cost() == entity.getReplacement_cost() &&
                            film.getRental_duration() == entity.getRental_duration() &&
                            film.getRental_rate() == entity.getRental_rate()*/;
                }
        ).findAny();
        if(!filmEntity.isPresent())
            throw new UnknownFilmException(String.format("Film Entity Not Found %d", film.getId()));
        return filmEntity.get();
    }

    @Override
    public void deleteFilm(int id) throws UnknownFilmException {
        Optional<FilmEntity> filmEntity = filmRepository.findById(id);
        if(!filmEntity.isPresent()){
            throw new UnknownFilmException(String.format("Film Entity Not Found %s",id));
        }
        filmRepository.delete(filmEntity.get());
    }
}
