package hu.unideb.webdev.dao.converter;

import hu.unideb.webdev.dao.enums.SpecialFeatures;

import java.util.ArrayList;
import java.util.List;

public class SpecialFeaturesList extends ArrayList<SpecialFeatures> {
    @Override
    public String toString() {
        List<String> lst = new ArrayList<String>();
        for (SpecialFeatures s : this)
            lst.add(s.name().replace("_", " "));
        return String.join(",", lst.toArray(new String[lst.size()]));
    }

    public static SpecialFeaturesList to(String str) {
        SpecialFeaturesList lst = new SpecialFeaturesList();
        if (str.isEmpty())
            return lst;

        String[] args = str.split(",");
        for (String s : args)
            lst.add(SpecialFeatures.valueOf(s.trim().replace(" ", "_")));
        return lst;
    }
}
