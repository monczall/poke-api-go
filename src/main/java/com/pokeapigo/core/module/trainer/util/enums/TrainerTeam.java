package com.pokeapigo.core.module.trainer.util.enums;

public enum TrainerTeam {

    INSTINCT("Instinct"),
    MYSTIC("Mystic"),
    VALOR("Valor"),
    NONE("None");

    private String value;

    TrainerTeam(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
