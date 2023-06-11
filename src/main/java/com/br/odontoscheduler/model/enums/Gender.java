package com.br.odontoscheduler.model.enums;

public enum Gender {
    MASCULINO("masculino"),
    FEMININO("feminino"),
    OUTRO("outro");

    private String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
