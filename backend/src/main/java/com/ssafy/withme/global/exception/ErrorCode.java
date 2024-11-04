package com.ssafy.withme.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

    /**Auth*/
    INVALID_ID_TOKEN(UNAUTHORIZED, "AUTH008", "ID 토큰 검증에 실패했습니다."),

    /**Repo*/
    REPO_NOT_FOUND(BAD_REQUEST, "REPO001", "해당 레파지토리가 존재하지 않습니다.");


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
