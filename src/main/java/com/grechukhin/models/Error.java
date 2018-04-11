package com.grechukhin.models;

public enum Error {

    OK(0),
    ERROR(1),
    WARNING(2);

    private int code;

    Error(int code) {
        this.code = code;
    }


    public int getCode() {
        return code;
    }

    public static Error getByCode(int code) {
        for (Error error : values()) {
            if (code == error.getCode()) {
                return error;
            }
        }
        throw new IllegalArgumentException("Unknown error code");
    }
}
