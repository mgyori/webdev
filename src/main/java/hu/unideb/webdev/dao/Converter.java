package hu.unideb.webdev.dao;

import hu.unideb.webdev.dao.entity.*;
import hu.unideb.webdev.model.*;

public class Converter {
    public static Inventory inventoryEntityToModel(InventoryEntity entity) {
        return new Inventory(entity.getId(), filmEntityToModel(entity.getFilm()), storeEntityToModel(entity.getStore()));
    }

    public static Address addressEntityToModel(AddressEntity entity) {
        return new Address(
                entity.getId(),
                entity.getAddress(),
                entity.getAddress2(),
                entity.getDistrict(),
                entity.getCity().getName(),
                entity.getCity().getCountry().getName(),
                entity.getPostalCode(),
                entity.getPhone()
        );
    }

    public static Staff staffEntityToModel(StaffEntity entity, Store store) {
        return new Staff(entity.getId(),
                entity.getFirst_name(),
                entity.getLast_name(),
                entity.getEmail(),
                //storeEntityToModel(entity.getStore()),
                store,
                entity.getUsername(),
                entity.getPassword(),
                addressEntityToModel(entity.getAddress()));
    }

    public static Store storeEntityToModel(StoreEntity entity) {
        if (entity == null)
            return null;
        return new Store(entity.getId(), staffEntityToModel(entity.getStaff(), null), addressEntityToModel(entity.getAddress()));
    }

    public static Film filmEntityToModel(FilmEntity entity) {
        return new Film(entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                (short) entity.getRelease_year().getValue(),
                entity.getLanguage().getName(),
                entity.getRental_duration(),
                entity.getRental_rate(),
                entity.getLength(),
                entity.getReplacement_cost(),
                entity.getRating().getRate(),
                entity.getSpecial_features().toString());
    }
}
