package hu.unideb.webdev.dao;

import hu.unideb.webdev.dao.entity.*;
import hu.unideb.webdev.exceptions.*;
import hu.unideb.webdev.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryDaoImpl implements InventoryDao {
    private final InventoryRepository inventoryRepository;
    private final FilmRepository filmRepository;
    private final StoreRepository storeRepository;

    private final FilmDao filmDao;
    private final StoreDao storeDao;

    @Override
    public InventoryEntity createInventory(Inventory inventory) throws UnknownFilmException, UnknownStoreException, UnknownLaungeageException, UnknownAddressException, UnknownStaffException {
        InventoryEntity inventoryEntity = InventoryEntity.builder()
                .film(queryFilm(inventory.getFilm()))
                .store(queryStore(inventory.getStore()))
                .build();
        try {
            return inventoryRepository.save(inventoryEntity);
        }
        catch(Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    protected FilmEntity queryFilm(Film film) throws UnknownFilmException, UnknownLaungeageException {
        Optional<FilmEntity> optionalFilmEntity = filmRepository.findById(film.getId());
        if (optionalFilmEntity.isPresent())
            return optionalFilmEntity.get();

        try {
            return filmDao.findByFilm(film);
        } catch (UnknownFilmException e) {
            return filmDao.createFilm(film);
        }
    }

    protected StoreEntity queryStore(Store store) throws UnknownStoreException, UnknownAddressException, UnknownStaffException {
        Optional<StoreEntity> optionalStoreEntity = storeRepository.findById(store.getId());
        if (optionalStoreEntity.isPresent())
            return optionalStoreEntity.get();

        StoreEntity storeEntity = storeDao.findByStore(store);
        if (storeEntity == null)
            storeEntity = storeDao.createStore(store);

        return storeEntity;
    }

    @Override
    public Collection<Inventory> readAll(Pageable page) {
        return StreamSupport.stream(inventoryRepository.findAll(page).spliterator(),false)
                .map(entity -> Converter.inventoryEntityToModel(entity))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteInventory(int id) throws UnknownInventoryException {
        Optional<InventoryEntity> inventoryEntity = inventoryRepository.findById(id);
        if(!inventoryEntity.isPresent()){
            throw new UnknownInventoryException(String.format("Inventory Entity Not Found %s",id), null);
        }
        inventoryRepository.delete(inventoryEntity.get());
    }

    @Override
    @Transactional
    public void updateInventory(Inventory inventory) throws UnknownInventoryException {
        Optional<InventoryEntity> ent = inventoryRepository.findById(inventory.getId()).map(rec -> {
            Optional<FilmEntity> filmEntity = filmRepository.findById(inventory.getFilm().getId());
            if (filmEntity.isPresent())
                rec.setFilm(filmEntity.get());
            Optional<StoreEntity> storeEntity = storeRepository.findById(inventory.getStore().getId());
            if (storeEntity.isPresent())
                rec.setStore(storeEntity.get());
            return rec;
        });
        if(!ent.isPresent())
            throw new UnknownInventoryException(String.format("Inventory Entity Not Found %s", inventory), inventory);
        inventoryRepository.save(ent.get());
    }
}
