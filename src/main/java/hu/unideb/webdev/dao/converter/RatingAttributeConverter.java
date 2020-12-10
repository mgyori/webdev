package hu.unideb.webdev.dao.converter;

import hu.unideb.webdev.dao.enums.Rating;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RatingAttributeConverter implements AttributeConverter<Rating, String> {
    @Override
    public String convertToDatabaseColumn(
            Rating attribute) {
        if (attribute != null) {
            return attribute.getRate();
        }
        return null;
    }

    @Override
    public Rating convertToEntityAttribute(
            String dbData) {
        if (dbData != null) {
            return Rating.valueOf(dbData.trim().replace("-", ""));
        }
        return null;
    }
}
