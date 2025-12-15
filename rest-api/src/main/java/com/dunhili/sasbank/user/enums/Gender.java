package com.dunhili.sasbank.user.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;

/**
 * Enum representing the different genders for a user.
 * <ul>
 *     <li>(M)ale</li>
 *     <li>(F)emale</li>
 *     <li>(N)ot Provided</li>
 * </ul>
 */
@Getter
public enum Gender {
    MALE("M"),
    FEMALE("F"),
    NOT_PROVIDED("N");

    private final String code;

    Gender(String code) {
        this.code = code;
    }

    @JsonCreator
    public static Gender fromString(final String code) {
        return Arrays.stream(Gender.values())
                .filter(gender -> gender.getCode().equals(code))
                .findFirst()
                .orElse(NOT_PROVIDED);
    }
}
