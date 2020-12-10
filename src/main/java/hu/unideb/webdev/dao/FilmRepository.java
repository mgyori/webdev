package hu.unideb.webdev.dao;

import hu.unideb.webdev.dao.entity.FilmEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface FilmRepository extends CrudRepository<FilmEntity, Integer> {
    Collection<FilmEntity> findByTitle(String title);
}
