package com.sunbird.serve.volunteering.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        JwtToken annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(JwtToken.class);
        } else {
            return true;
        }
        // If there is no such comment, let go directly
        if (annotation == null) {
            return true;
        }
        // Verification token
        ResponseResult res = JwtUtil.verify();
        if (200 == res.getCode()) {
            return true;
        }
        // If the verification fails, 401 is returned, indicating that the user is not logged in
        response.setStatus(401);
        response.sendError(res.getCode(), res.getMsg());
        return false;
    }
}