package ru.noname.company.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TodoBodyParams {
    ID("id"),
    TEXT("text"),
    COMPLETED("completed");

    private final String value;
}
