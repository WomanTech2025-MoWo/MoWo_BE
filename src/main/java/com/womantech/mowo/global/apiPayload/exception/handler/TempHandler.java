package com.womantech.mowo.global.apiPayload.exception.handler;

import com.womantech.mowo.global.apiPayload.code.BaseErrorCode;
import com.womantech.mowo.global.apiPayload.exception.GeneralException;

public class TempHandler extends GeneralException {

    public TempHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}