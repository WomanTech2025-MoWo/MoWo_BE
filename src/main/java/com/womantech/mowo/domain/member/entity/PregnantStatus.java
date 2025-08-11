package com.womantech.mowo.domain.member.entity;

public enum PregnantStatus {
    PREGNANT("임신중"),
    PREPARING_FOR_PREGNANCY("임신준비중"),
    NOT_PREGNANT("임신여부");

    private final String description;

    PregnantStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}