package com.clases.interactivas.clases_practicas.enums;
import lombok.Getter;
import java.util.Arrays;

@Getter
public enum SortTypeEnum {
    ASCENDANT("asc"),
    DESCENDANT("desc");

    private final String code;

    SortTypeEnum(String code) {
        this.code = code;
    }

    public static SortTypeEnum getEnum(String code){
        return Arrays.stream(SortTypeEnum.values())
        .filter(e -> e.getCode().equalsIgnoreCase(code))
        .findAny()
        .orElse(SortTypeEnum.ASCENDANT);
    }
}
