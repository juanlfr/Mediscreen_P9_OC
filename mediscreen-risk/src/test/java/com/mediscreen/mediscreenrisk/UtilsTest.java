package com.mediscreen.mediscreenrisk;

import com.mediscreen.mediscreenrisk.utils.RiskUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilsTest {

    private static final String accents 	= "È,É,Ê,Ë,Û,Ù,Ï,Î,À,Â,Ô,è,é,ê,ë,û,ù,ï,î,à,â,ô,Ç,ç,Ã,ã,Õ,õ";
    private static final String expected	= "E,E,E,E,U,U,I,I,A,A,O,e,e,e,e,u,u,i,i,a,a,o,C,c,A,a,O,o";

    @Test
    public void replacingAllAccents() {
        assertEquals(expected, RiskUtils.unaccent(accents));
    }

    @Test
    public void calculateAgeTest() {
        int calculatedAge = RiskUtils.calculateAge(LocalDate.of(1991,9,6));
        assertEquals(30, calculatedAge);
    }
}
