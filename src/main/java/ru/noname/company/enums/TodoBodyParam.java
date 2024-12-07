package ru.noname.company.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// Can be redone using JSON Object, but the current implementation is also convenient
@RequiredArgsConstructor
@Getter
public enum TodoBodyParam {
    ID("id"),
    TEXT("text"),
    COMPLETED("completed");

    private final String value;
}
