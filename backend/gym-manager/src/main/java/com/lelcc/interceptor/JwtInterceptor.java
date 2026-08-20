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
    private JwtUtil jwtutil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("interceptor: " + request.getRequestURI());
        // 1.获取请求头中的关于token的内容，目前设置为Authorization
        String token = request.getHeader("Authorization");
        // 如果token是空的
        if(!StringUtils.hasText(token)||!token.startsWith("Bearer ")){
            System.out.println(token);
            throw new BusinessException(401,"请先登录");
        }
        String realToken = token.substring(7);
        if (!jwtutil.validateToken(realToken)) {
            throw new BusinessException(401,"传入的token无效");
        }
        Long userId = jwtutil.getUserId(realToken);
        UserContext.set(userId);

        return true;

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        UserContext.remove();
    }
}
