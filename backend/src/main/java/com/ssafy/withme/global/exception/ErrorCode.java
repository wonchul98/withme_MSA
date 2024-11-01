package com.ssafy.withme.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    INVALID_ID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH008", "ID 토큰 검증에 실패했습니다."),

    WORKSPACE_NOT_FOUND(HttpStatus.NOT_FOUND, "WORK000", "해당하는 ID의 워크스페이스가 존재하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    public int getStatus(){
        return this.status.value();
    }

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
