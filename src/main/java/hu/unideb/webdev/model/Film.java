package hu.unideb.webdev.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
@EqualsAndHashCode
public class Film {
    private int id;
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
