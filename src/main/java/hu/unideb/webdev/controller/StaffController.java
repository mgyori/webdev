package hu.unideb.webdev.controller;

import hu.unideb.webdev.controller.dto.InventoryDto;
import hu.unideb.webdev.controller.dto.StaffDto;
import hu.unideb.webdev.controller.dto.StaffRecordRequestDto;
import hu.unideb.webdev.exceptions.UnknownAddressException;
import hu.unideb.webdev.exceptions.UnknownStaffException;
import hu.unideb.webdev.exceptions.UnknownStoreException;
import hu.unideb.webdev.model.Address;
import hu.unideb.webdev.model.Staff;
import hu.unideb.webdev.service.StaffService;
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
public class StaffController {
    private final StaffService service;

    @GetMapping("/staff")
    public Collection<StaffDto> listStaff() {
        return service.getAllStaff().stream().map(model -> modelToDto(model)).collect(Collectors.toList());
    }

    private StaffDto modelToDto(Staff model) {
        return StaffDto.builder()
                .id(model.getId())
                .first_name(model.getFirst_name())
                .last_name(model.getLast_name())
                .email(model.getEmail())
                .username(model.getUsername())
                .password(model.getPassword())
                .build();
    }

    @PostMapping("/staff")
    public String record(@RequestBody StaffRecordRequestDto request)
    {
        try {
            service.recordStaff(new Staff(0,
                    request.getFirst_name(),
                    request.getLast_name(),
                    request.getEmail(),
                    null,
                    request.getUsername(),
                    request.getPassword(),
                    new Address(0,
                            request.getAddress().getAddress(),
                            request.getAddress().getAddress2(),
                            request.getAddress().getDistrict(),
                            request.getAddress().getCity(),
                            request.getAddress().getCountry(),
                            request.getAddress().getPostalCode(),
                            request.getAddress().getPhone()
                    )));
        } catch (UnknownAddressException | UnknownStoreException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return "Staff successfully added!";
    }

    @PutMapping("/staff/{id}")
    public String update(@PathVariable("id") int id, @RequestBody StaffRecordRequestDto request)
    {
        try {
            service.updateStaff(new Staff(id,
                    request.getFirst_name(),
                    request.getLast_name(),
                    request.getEmail(),
                    null,
                    request.getUsername(),
                    request.getPassword(),
                    new Address(0,
                            request.getAddress().getAddress(),
                            request.getAddress().getAddress2(),
                            request.getAddress().getDistrict(),
                            request.getAddress().getCity(),
                            request.getAddress().getCountry(),
                            request.getAddress().getPostalCode(),
                            request.getAddress().getPhone()
                    )));
        } catch (UnknownAddressException | UnknownStaffException | UnknownStoreException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return "Staff successfully updated!";
    }

    @DeleteMapping(path ={"/staff/{id}"})
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        try {
            service.deleteStaff(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
