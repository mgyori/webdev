package hu.unideb.webdev.dao;

import hu.unideb.webdev.dao.entity.LangueageEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LangueageRepository extends CrudRepository<LangueageEntity, Integer> {
    @Override
    Optional<LangueageEntity> findById(Integer integer);
    Optional<LangueageEntity> findByName(String name);
}
