package hu.unideb.webdev.dao.converter;

import hu.unideb.webdev.dao.enums.SpecialFeatures;

import javax.persistence.AttributeConverter;

public class SpecialFeaturesAttributeConverter implements AttributeConverter<SpecialFeaturesList, String> {
    @Override
    public String convertToDatabaseColumn(
            SpecialFeaturesList attribute) {
        if (attribute != null) {
            return attribute.toString();
        }
        return null;
    }

    @Override
    public SpecialFeaturesList convertToEntityAttribute(
            String dbData) {
        if (dbData != null) {
            return SpecialFeaturesList.to(dbData);
        }
        return null;
    }
}
