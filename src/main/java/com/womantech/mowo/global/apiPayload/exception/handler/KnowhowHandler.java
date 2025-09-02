package com.womantech.mowo.global.apiPayload.exception.handler;

import com.womantech.mowo.global.apiPayload.code.BaseErrorCode;
import com.womantech.mowo.global.apiPayload.exception.GeneralException;

public class KnowhowHandler extends GeneralException {
  public KnowhowHandler(BaseErrorCode errorCode) {
    super(errorCode);
  }
}