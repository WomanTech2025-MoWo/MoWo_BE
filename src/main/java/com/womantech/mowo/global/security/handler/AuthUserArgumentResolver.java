package com.womantech.mowo.global.security.handler;

import com.womantech.mowo.global.apiPayload.code.status.ErrorStatus;
import com.womantech.mowo.global.apiPayload.exception.handler.AuthHandler;
import com.womantech.mowo.global.security.userDetails.LocalCustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthUser.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory)
            throws AuthHandler {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = null;

        if (authentication != null) {
            if (authentication.getName().equals("anonymousUser")) {
                throw new AuthHandler(ErrorStatus._UNAUTHORIZED);
            }
            principal = authentication.getPrincipal();
        }
        if (principal == null || principal.getClass() == String.class) {
            throw new AuthHandler(ErrorStatus.MEMBER_NOT_FOUND);
        }

        if (principal instanceof LocalCustomUserDetails) {
            LocalCustomUserDetails userDetails = (LocalCustomUserDetails) principal;
            return userDetails.getId();
        }
        throw new AuthHandler(ErrorStatus.MEMBER_NOT_FOUND);
    }
}