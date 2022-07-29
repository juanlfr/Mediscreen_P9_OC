package com.mediscreen.mediscreenrisk.utils;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.Period;

public class RiskUtils {

    public static String unaccent(String src) {
        return Normalizer
                .normalize(src, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }
    public static int calculateAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

}
