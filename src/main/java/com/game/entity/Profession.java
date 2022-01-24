package com.game.entity;

import java.io.Serializable;

public enum Profession implements Serializable {
    WARRIOR("WARRIOR"),
    ROGUE("ROGUE"),
    SORCERER("SORCERER"),
    CLERIC("CLERIC"),
    PALADIN("PALADIN"),
    NAZGUL("NAZGUL"),
    WARLOCK("WARLOCK"),
    DRUID("DRUID");
    private final String fieldName;

    Profession(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static Profession getProByName(String fieldName) {
        for (Profession pro : values()) {
            if (pro.getFieldName().equalsIgnoreCase(fieldName)) {
                return pro;
            }
        }
        return null;
    }
}
