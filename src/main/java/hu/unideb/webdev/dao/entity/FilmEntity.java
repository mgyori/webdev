package hu.unideb.webdev.dao.entity;

import hu.unideb.webdev.dao.converter.RatingAttributeConverter;
import hu.unideb.webdev.dao.converter.SpecialFeaturesAttributeConverter;
import hu.unideb.webdev.dao.converter.SpecialFeaturesList;
import hu.unideb.webdev.dao.converter.YearAttributeConverter;
import hu.unideb.webdev.dao.enums.Rating;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Year;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "film", schema = "sakila")
public class FilmEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private int id;

    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;

    @Column(name="release_year")
    @Convert(converter = YearAttributeConverter.class)
    private Year release_year;

    @ManyToOne
    @JoinColumn(name="language_id")
    private LangueageEntity language;

    @ManyToOne
    @JoinColumn(name="original_language_id")
    private LangueageEntity original_language_id;

    @Column(name="rental_duration")
    private short rental_duration;

    @Column(name="rental_rate")
    private float rental_rate;

    @Column(name="length")
    private int length;

    @Column(name="replacement_cost")
    private float replacement_cost;

    @Column(name="rating")
    @Convert(converter = RatingAttributeConverter.class)
    private Rating rating;

    @Column(name="special_features")
    @Convert(converter = SpecialFeaturesAttributeConverter.class)
    private SpecialFeaturesList special_features;

    @Column(name ="last_update")
    private Timestamp lastUpdate;
}
