package com.womantech.mowo.global.apiPayload.exception.handler;

import com.womantech.mowo.global.apiPayload.code.BaseErrorCode;
import com.womantech.mowo.global.apiPayload.exception.GeneralException;

public class AuthHandler extends GeneralException {
    public AuthHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}