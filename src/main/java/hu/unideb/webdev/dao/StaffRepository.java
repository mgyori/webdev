package hu.unideb.webdev.dao;

import hu.unideb.webdev.dao.entity.StaffEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface StaffRepository extends CrudRepository<StaffEntity, Integer> {
    Collection<StaffEntity> findByUsername(String username);
}
