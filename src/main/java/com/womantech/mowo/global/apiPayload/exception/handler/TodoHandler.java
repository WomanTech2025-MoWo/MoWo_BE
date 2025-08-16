package com.womantech.mowo.global.apiPayload.exception.handler;

import com.womantech.mowo.global.apiPayload.code.BaseErrorCode;
import com.womantech.mowo.global.apiPayload.exception.GeneralException;

public class TodoHandler extends GeneralException {
    public TodoHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
