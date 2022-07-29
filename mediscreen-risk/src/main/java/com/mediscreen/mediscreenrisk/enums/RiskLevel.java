package com.mediscreen.mediscreenrisk.enums;

public enum RiskLevel {

        NONE("None"),
        BORDERLINE("Borderline"),
        DANGER("Danger"),
        EARLY_ONSET("Early onset"),
        UNDEFINED("Undefined");

        public final String label;


        RiskLevel(String label) {
                this.label = label;
        }
}
