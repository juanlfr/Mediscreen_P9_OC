package com.mediscreen.mediscreenrisk.enums;

public enum TriggerWords {

    HEMOGLOBINE_A1C("hémoglobine a1c"),
    MICROALBUMINE("microalbumine"),
    TAILLE("taille"),
    POIDS("poids"),
    FUMEUR("fumeur"),
    ANORMAL("anormal"),
    CHOLESTEROL("cholestérol"),
    VERTIGE("vertige"),
    RECHUTE("rechute"),
    REACTION("réaction"),
    ANTICORPS("anticorps");

    public final String label;

    TriggerWords(String label) {
        this.label = label;
    }

    }
