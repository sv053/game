package com.game.entity;

import java.io.Serializable;

public enum Race implements Serializable {
    HUMAN("HUMAN"),
    DWARF("DWARF"),
    ELF("ELF"),
    GIANT("GIANT"),
    ORC("ORC"),
    TROLL("TROLL"),
    HOBBIT("HOBBIT");

    private final String fieldName;

    Race(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static Race getRaceByName(String fieldName) {
        for (Race race : values()) {
            if (race.getFieldName().equalsIgnoreCase(fieldName)) {
                return race;
            }
        }
        return null;
    }
}