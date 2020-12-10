package hu.unideb.webdev.controller;

import hu.unideb.webdev.controller.dto.*;
import hu.unideb.webdev.dao.StoreDao;
import hu.unideb.webdev.exceptions.UnknownAddressException;
import hu.unideb.webdev.exceptions.UnknownStaffException;
import hu.unideb.webdev.exceptions.UnknownStoreException;
import hu.unideb.webdev.model.Address;
import hu.unideb.webdev.model.Staff;
import hu.unideb.webdev.model.Store;
import hu.unideb.webdev.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StoreController {
    private final StoreService service;

    @GetMapping("/store")
    public Collection<StoreDto> listAllStore() {
        return service.getAllStore().stream().map(model -> modelToDto(model)).collect(Collectors.toList());
    }

    private StoreDto modelToDto(Store model) {
        return StoreDto.builder().staff(
                StaffDto.builder().first_name(model.getStaff().getFirst_name())
                .id(model.getStaff().getId())
                .last_name(model.getStaff().getLast_name())
                .email(model.getStaff().getEmail())
                .username(model.getStaff().getUsername())
                .password(model.getStaff().getPassword()).build())
                .address(AddressRecordResquestDto.builder()
                        .address(model.getAddress().getAddress())
                        .address2(model.getAddress().getAddress2())
                        .district(model.getAddress().getDistrict())
                        .city(model.getAddress().getCity())
                        .postalCode(model.getAddress().getPostalCode())
                        .phone(model.getAddress().getPhone())
                        .build())
                .build();
    }

    @PostMapping("/store")
    public String record(@RequestBody StaffRecordRequestDto request)
    {
        try {
            Address addr = new Address(0,
                    request.getAddress().getAddress(),
                    request.getAddress().getAddress2(),
                    request.getAddress().getDistrict(),
                    request.getAddress().getCity(),
                    request.getAddress().getCountry(),
                    request.getAddress().getPostalCode(),
                    request.getAddress().getPhone()
            );
            service.recordStore(
                    new Store(0,
                        new Staff(0,
                            request.getFirst_name(),
                            request.getLast_name(),
                            request.getEmail(),
                            null,
                            request.getUsername(),
                            request.getPassword(),
                            addr
                            ),
                            addr)
            );
        } catch (UnknownAddressException | UnknownStoreException | UnknownStaffException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return "Store successfully added!";
    }

    @PutMapping("/store/{id}")
    public String update(@PathVariable("id") int id, @RequestBody StaffRecordRequestDto request)
    {
        try {
            Address addr = new Address(0,
                    request.getAddress().getAddress(),
                    request.getAddress().getAddress2(),
                    request.getAddress().getDistrict(),
                    request.getAddress().getCity(),
                    request.getAddress().getCountry(),
                    request.getAddress().getPostalCode(),
                    request.getAddress().getPhone()
            );

            service.updateStore(new Store(id,
                    new Staff(0,
                            request.getFirst_name(),
                            request.getLast_name(),
                            request.getEmail(),
                            null,
                            request.getUsername(),
                            request.getPassword(),
                            addr
                    ),
                    addr));
        } catch (UnknownAddressException | UnknownStaffException | UnknownStoreException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return "Store successfully updated!";
    }

    @DeleteMapping(path ={"/store/{id}"})
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        try {
            service.deleteStore(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
