package hu.unideb.webdev;

import hu.unideb.webdev.dao.Converter;
import hu.unideb.webdev.dao.converter.SpecialFeaturesList;
import hu.unideb.webdev.dao.entity.*;
import hu.unideb.webdev.dao.enums.Rating;
import hu.unideb.webdev.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.Year;
import java.util.Arrays;
import java.util.Collection;

public class TestData {
    public static Page<InventoryEntity> getInventoryEntities() {
        return new PageImpl<InventoryEntity>(Arrays.asList(getInventoryEntity()));
    }

    public static Collection<Inventory> getInventories() {
        return Arrays.asList(getInventory());
    }

    public static InventoryEntity getInventoryEntity() {
        return InventoryEntity.builder()
                .store(getStoreEntity())
                .film(getFilmEntity())
                .build();
    }

    public static Inventory getInventory() {
        return Converter.inventoryEntityToModel(getInventoryEntity());
    }

    public static Store getStore() {
        return Converter.storeEntityToModel(getStoreEntity());
    }

    public static StoreEntity getStoreEntity() {
        return StoreEntity.builder()
                .staff(getStaffEntity())
                .address(getAddressEntity())
                .build();
    }

    public static Staff getStaff() {
        return Converter.staffEntityToModel(getStaffEntity(), null);
    }

    public static StaffEntity getStaffEntity() {
        return StaffEntity.builder()
                .first_name("Teszt ")
                .last_name("Staff")
                .email("asd@asd.hu")
                .username("username")
                .password("password")
                .active(1)
                //.store(getStoreEntity())
                .address(getAddressEntity())
                .build();
    }

    public static Film getFilm() {
        return Converter.filmEntityToModel(getFilmEntity());
    }

    public static FilmEntity getFilmEntity() {
        return FilmEntity.builder()
                .title("Teszt film")
                .description("Leírás")
                .release_year(Year.parse("2016"))
                .language(LangueageEntity.builder().name("English").build())
                .rental_duration((short) 1)
                .rental_rate(1)
                .length(1)
                .replacement_cost(1)
                .rating(Rating.PG13)
                .special_features(SpecialFeaturesList.to(""))
                .build();
    }

    public static Address getAddress() {
        return Converter.addressEntityToModel(getAddressEntity());
    }

    public static AddressEntity getAddressEntity() {
        return AddressEntity.builder()
                .address("address1")
                .address2("address2")
                .district("district")
                .city(CityEntity.builder().name("City").country(CountryEntity.builder().name("Algeria_1234").build()).build())
                .postalCode("postalCode")
                .phone("phone")
                .build();
    }

}
