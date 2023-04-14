package org.simelabs.catelog.application.service.models;

import lombok.Getter;

@Getter
public enum ProductSize {
    S("Small"),M("Medium"),L("Large"),XL("Extra Large"),XXL("Extra Extra Large");

    private final String value;

    ProductSize(String value) {
        this.value = value;
    }
}
