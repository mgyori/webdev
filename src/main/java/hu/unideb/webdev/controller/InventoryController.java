package hu.unideb.webdev.controller;

import hu.unideb.webdev.controller.dto.*;
import hu.unideb.webdev.dao.entity.InventoryEntity;
import hu.unideb.webdev.exceptions.*;
import hu.unideb.webdev.model.*;
import hu.unideb.webdev.service.InventoryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/inventory")
    public Collection<InventoryDto> listInventories()
    {
        return inventoryService.getAllInventories().stream().map(model -> modelToDto(model)).collect(Collectors.toList());
    }

    @ApiOperation("Query a specified number of inventory rows.")
    @GetMapping("/inventory-page")
    public Collection<InventoryDto> listInventories(@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="limit", defaultValue="20") int limit)
    {
        return inventoryService.getAllInventories(PageRequest.of(page, limit)).stream().map(model -> modelToDto(model)).collect(Collectors.toList());
    }

    private InventoryDto modelToDto(Inventory model) {
        return InventoryDto.builder()
            .film(FilmDto.builder()
                    .title(model.getFilm().getTitle())
                    .description(model.getFilm().getDescription())
                    .release_year(model.getFilm().getRelease_year())
                    .language(model.getFilm().getLanguage())
                    .rental_duration(model.getFilm().getRental_duration())
                    .rental_rate(model.getFilm().getRental_rate())
                    .length(model.getFilm().getLength())
                    .replacement_cost(model.getFilm().getReplacement_cost())
                    .rating(model.getFilm().getRating())
                    .special_features(model.getFilm().getSpecial_features())
                    .build())
            .store(StoreDto.builder()
                    .address(AddressRecordResquestDto.builder()
                            .address(model.getStore().getAddress().getAddress())
                            .address2(model.getStore().getAddress().getAddress2())
                            .district(model.getStore().getAddress().getDistrict())
                            .city(model.getStore().getAddress().getCity())
                            .country(model.getStore().getAddress().getCountry())
                            .phone(model.getStore().getAddress().getPhone())
                            .postalCode(model.getStore().getAddress().getPostalCode())
                            .build())
                    .staff(StaffDto.builder()
                            .first_name(model.getStore().getStaff().getFirst_name())
                            .last_name(model.getStore().getStaff().getLast_name())
                            .email(model.getStore().getStaff().getEmail())
                            .username(model.getStore().getStaff().getUsername())
                            .password(model.getStore().getStaff().getPassword())
                            .build())
                    .build())
            .build();
    }

    @ApiOperation("Insert a specified number of inventory row.")
    @PostMapping("/inventory")
    public String record(@RequestBody InventoryUpdateRequestDto requestDto) {
        try {
            inventoryService.recordInventory(
                    new Inventory(requestDto.getId(),
                            new Film(requestDto.getFilm(), null, null, null, null, 0, 0, 0, 0, null, null),
                            new Store(requestDto.getStore(), null, null))
            );
        } catch (UnknownFilmException | UnknownStoreException | UnknownLaungeageException | UnknownAddressException | UnknownStaffException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return "Insert was successful!";
    }

    @PostMapping("/inventoryall")
    public String record(@RequestBody InventoryDto inv) {
        try {
            StoreDto store = inv.getStore();
            FilmDto film = inv.getFilm();

            Address addr = new Address(0,
                    store.getAddress().getAddress(),
                    store.getAddress().getAddress2(),
                    store.getAddress().getDistrict(),
                    store.getAddress().getCity(),
                    store.getAddress().getCountry(),
                    store.getAddress().getPostalCode(),
                    store.getAddress().getPhone()
            );

            inventoryService.recordInventory(
                    new Inventory(0,
                            new Film(
                            0,
                                    film.getTitle(),
                                    film.getDescription(),
                                    film.getRelease_year(),
                                    film.getLanguage(),
                                    film.getRental_duration(),
                                    film.getRental_rate(),
                                    film.getLength(),
                                    film.getReplacement_cost(),
                                    film.getRating(),
                                    film.getSpecial_features()),
                            new Store(0, new Staff(0,
                                    store.getStaff().getFirst_name(),
                                    store.getStaff().getLast_name(),
                                    store.getStaff().getEmail(),
                                    null,
                                    store.getStaff().getUsername(),
                                    store.getStaff().getPassword(),
                                    addr), addr))
            );
        } catch (UnknownFilmException | UnknownStoreException | UnknownLaungeageException | UnknownAddressException | UnknownStaffException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return "Insert was successful!";
    }

    @PutMapping("/inventoryall/{id}")
    public String updateall(@PathVariable("id") int id, @RequestBody InventoryDto inv) {
        try {
            StoreDto store = inv.getStore();
            FilmDto film = inv.getFilm();

            Address addr = new Address(0,
                    store.getAddress().getAddress(),
                    store.getAddress().getAddress2(),
                    store.getAddress().getDistrict(),
                    store.getAddress().getCity(),
                    store.getAddress().getCountry(),
                    store.getAddress().getPostalCode(),
                    store.getAddress().getPhone()
            );

            inventoryService.updateInventory(
                    new Inventory(id,
                            new Film(
                                    0,
                                    film.getTitle(),
                                    film.getDescription(),
                                    film.getRelease_year(),
                                    film.getLanguage(),
                                    film.getRental_duration(),
                                    film.getRental_rate(),
                                    film.getLength(),
                                    film.getReplacement_cost(),
                                    film.getRating(),
                                    film.getSpecial_features()),
                            new Store(0, new Staff(0,
                                    store.getStaff().getFirst_name(),
                                    store.getStaff().getLast_name(),
                                    store.getStaff().getEmail(),
                                    null,
                                    store.getStaff().getUsername(),
                                    store.getStaff().getPassword(),
                                    addr), addr))
            );
        } catch (UnknownInventoryException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return "Insert was updated!";
    }

    @ApiOperation("Updates a specified number of inventory rows.")
    @PutMapping("/inventory")
    public String update(@RequestBody InventoryUpdateRequestDto requestDto)
    {
        try {
            inventoryService.updateInventory(new Inventory(requestDto.getId(),
                    new Film(requestDto.getFilm(), null, null, null, null, 0, 0, 0, 0, null, null),
                    new Store(requestDto.getStore(), null, null)));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return "Inventory element successfully updated!";
    }

    @ApiOperation("Deletes a specified number of inventory rows.")
    @DeleteMapping("/inventory/{filmId}")
    public String delete(@PathVariable("filmId") int filmId)
    {
        try {
            inventoryService.deleteInventory(filmId);
        } catch (UnknownInventoryException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return "Inventory row succesfully deleted!";
    }
}
