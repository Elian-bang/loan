package org.example.loan.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultType {

    SUCCESS("0000", "success"),

    // FILE = 4000
    FILE_NOT_EXIST("4001", "file not exist"),

    SYSTEM_ERROR("9000", "system error");

    private final String code;
    private final String desc;
}
