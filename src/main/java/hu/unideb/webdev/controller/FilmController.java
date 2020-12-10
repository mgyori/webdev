package hu.unideb.webdev.controller;

import hu.unideb.webdev.controller.dto.FilmDto;
import hu.unideb.webdev.controller.dto.StaffDto;
import hu.unideb.webdev.controller.dto.StaffRecordRequestDto;
import hu.unideb.webdev.dao.entity.FilmEntity;
import hu.unideb.webdev.exceptions.UnknownAddressException;
import hu.unideb.webdev.exceptions.UnknownFilmException;
import hu.unideb.webdev.exceptions.UnknownLaungeageException;
import hu.unideb.webdev.model.Address;
import hu.unideb.webdev.model.Film;
import hu.unideb.webdev.model.Staff;
import hu.unideb.webdev.service.FilmService;
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
public class FilmController {
    private final FilmService service;

    @GetMapping("/film")
    public Collection<FilmDto> listFilms() {
        return service.getAllFilm().stream().map(model -> modelToDto(model)).collect(Collectors.toList());
    }

    private FilmDto modelToDto(Film model) {
        return FilmDto.builder()
                .title(model.getTitle())
                .description(model.getDescription())
                .release_year(model.getRelease_year())
                .language(model.getLanguage())
                .rental_duration(model.getRental_duration())
                .rental_rate(model.getRental_rate())
                .length(model.getLength())
                .replacement_cost(model.getReplacement_cost())
                .rating(model.getRating())
                .special_features(model.getSpecial_features())
                .build();
    }

    @PostMapping("/film")
    public String record(@RequestBody FilmDto request)
    {
        service.recordFilm(new Film(
                0,
                request.getTitle(),
                request.getDescription(),
                request.getRelease_year(),
                request.getLanguage(),
                request.getRental_duration(),
                request.getRental_rate(),
                request.getLength(),
                request.getReplacement_cost(),
                request.getRating(),
                request.getSpecial_features()
        ));
        return "Film successfully added!";
    }

    @PutMapping("/film/{id}")
    public String update(@PathVariable("id") int id, @RequestBody FilmDto request)
    {
        try {
            service.updateFilm(new Film(
                    id,
                    request.getTitle(),
                    request.getDescription(),
                    request.getRelease_year(),
                    request.getLanguage(),
                    request.getRental_duration(),
                    request.getRental_rate(),
                    request.getLength(),
                    request.getReplacement_cost(),
                    request.getRating(),
                    request.getSpecial_features()
            ));
        } catch (UnknownFilmException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return "Film successfully updated!";
    }

    @DeleteMapping(path ={"/film/{id}"})
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        try {
            service.deleteFilm(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
