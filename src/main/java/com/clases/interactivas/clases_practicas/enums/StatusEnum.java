package com.clases.interactivas.clases_practicas.enums;
import lombok.Getter;
import java.util.Arrays;

@Getter
public enum StatusEnum {
    ONLINE("en linea", "online", 1),
    OFFLINE("desconectado", "offline", 2),
    AWAY("ocupado", "away" ,2);

    private final String valueInApi;
    private final String valueInDb;
    private final Integer valueInDbInt;

    StatusEnum(String valueInApi, String valueInDb, Integer valueInDbInt) {
        this.valueInApi = valueInApi;
        this.valueInDb = valueInDb;
        this.valueInDbInt = valueInDbInt;        
    }

    public static StatusEnum getEnum(String valueApiOrDb) {
        return Arrays.stream(StatusEnum.values())
                .filter(e -> e.getValueInApi().equalsIgnoreCase(valueApiOrDb) || e.getValueInDb().equalsIgnoreCase(valueApiOrDb))
                .findAny()
                .orElse(null);
    }
}
