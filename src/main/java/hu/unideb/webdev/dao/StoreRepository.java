package hu.unideb.webdev.dao;

import hu.unideb.webdev.dao.entity.StoreEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface StoreRepository extends CrudRepository<StoreEntity, Integer> {
    //Collection<StoreEntity> findByName(String name);
}
