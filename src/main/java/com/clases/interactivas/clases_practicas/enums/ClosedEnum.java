package com.clases.interactivas.clases_practicas.enums;

import lombok.Getter;
import java.util.Arrays;

@Getter
public enum ClosedEnum {
    CLOSED(true, "si"),
    UNCLOSED(false, "no");

    private final Boolean valueInApi;
    private final String valueInDb;

    ClosedEnum(Boolean valueInApi, String valueInDb) {
        this.valueInApi = valueInApi;
        this.valueInDb = valueInDb;
    }

    public static ClosedEnum getEnum(Boolean valueApiOrDb){
        return (valueApiOrDb != null) ? Arrays.stream(ClosedEnum.values())
        .filter(e -> e.getValueInApi() == valueApiOrDb)
        .findAny()
        .orElse(ClosedEnum.UNCLOSED) : ClosedEnum.UNCLOSED;
    }

    public static ClosedEnum getEnum(String valueApiOrDb){
        return Arrays.stream(ClosedEnum.values())
        .filter(e -> e.getValueInDb().equalsIgnoreCase(valueApiOrDb))
        .findAny()
        .orElse(ClosedEnum.UNCLOSED);
    }
}
