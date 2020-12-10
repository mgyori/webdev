package hu.unideb.webdev.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FilmDto {
    private String title;
    private String description;
    private Short release_year;
    private String language;
    private int rental_duration;
    private float rental_rate;
    private int length;
    private float replacement_cost;
    private String rating;
    private String special_features;
}
