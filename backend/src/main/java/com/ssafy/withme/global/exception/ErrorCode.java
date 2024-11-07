package com.ssafy.withme.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

    /**Auth*/
    INVALID_ID_TOKEN(UNAUTHORIZED, "AUTH001", "ID 토큰 검증에 실패했습니다."),

    /**Member*/
    MEMBER_NOT_FOUND(BAD_REQUEST, "MEM001", "유저를 찾을 수 없습니다. "),

    /**Repo*/
    REPO_NOT_FOUND(BAD_REQUEST, "REPO001", "해당 레파지토리가 존재하지 않습니다."),
    REPO_NOT_ALLOWED(BAD_REQUEST, "REPO002", "해당 레파지토리에 접근할 권한이 없습니다. "),

    /**WorkSpace*/
    WORKSPACE_NOT_FOUND(BAD_REQUEST,"WORK000", "해당하는 ID의 워크스페이스가 존재하지 않습니다."),
    IMAGE_UPLOAD_FAILED(BAD_REQUEST, "WORK001", "이미지 업로드에 실패했습니다.");


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
