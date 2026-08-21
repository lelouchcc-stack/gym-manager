package com.lelcc.interceptor;

import com.lelcc.common.BusinessException;
import com.lelcc.common.UserContext;
import com.lelcc.utils.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Resource
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if(!StringUtils.hasText(token)||!token.startsWith("Bearer ")){
            throw new BusinessException(401,"请先登录");
        }
        String realToken = token.substring(7);
        if(!jwtUtil.validateToken(realToken)){
            throw new BusinessException(401,"验证过期");
        };
        Long user_id = jwtUtil.getUserId(realToken);
        UserContext.set(user_id);
        return true;

    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.remove();
    }
}
