package com.jc.cz_index.core.web.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

public abstract interface AuthenticationSuccessEvent {
    public abstract void onSuccess(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse,
            Authentication paramAuthentication) throws Exception;
}