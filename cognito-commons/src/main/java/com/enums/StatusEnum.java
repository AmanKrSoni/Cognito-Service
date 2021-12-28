package com.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The enum Status enum.
 */
public enum StatusEnum {

    /**
     * Requested status enum.
     */
    REQUESTED("REQUESTED"),
    /**
     * Granted status enum.
     */
    GRANTED("GRANTED"),
    /**
     * Expired status enum.
     */
    EXPIRED("EXPIRED"),
    /**
     * Revoked status enum.
     */
    REVOKED("REVOKED"),
    /**
     * DENIED status enum.
     */
    DENIED("DENIED"),
    /**
     * All status enum.
     */
    ALL("ALL");

    private String value;

     StatusEnum(String value) {
        this.value = value;
    }

    /**
     * Get value string.
     *
     * @return the string
     */
    @JsonValue
    public String getValue(){return value;}

    /**
     * From value status enum.
     *
     * @param value the value
     * @return the status enum
     */
    @JsonCreator
    public static StatusEnum fromValue(String value) {
        for (StatusEnum b : StatusEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
