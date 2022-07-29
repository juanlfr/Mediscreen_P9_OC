package com.mediscreen.mediscreenrisk.enums;

public enum TriggerWords {

    HEMOGLOBINE_A1C("hemoglobine a1c"),
    MICROALBUMINE("microalbumine"),
    TAILLE("taille"),
    POIDS("poids"),
    FUMEUR("fumeur"),
    ANORMAL("anormal"),
    CHOLESTEROL("cholesterol"),
    VERTIGE("vertige"),
    RECHUTE("rechute"),
    REACTION("reaction"),
    ANTICORPS("anticorps");

    public final String label;

    TriggerWords(String label) {
        this.label = label;
    }


    }
