package hu.unideb.webdev.dao;

import hu.unideb.webdev.dao.entity.InventoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

public interface InventoryRepository extends PagingAndSortingRepository<InventoryEntity, Integer> {
}
