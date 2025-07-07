package com.hmall.interceptor;

import com.hmall.common.utils.UserContext;
import com.hmall.utils.JwtTool;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTool jwtTool;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.获取请求头中的 token
        String token = request.getHeader("authorization");
        
        // 2.特殊处理：如果是admin，设置一个默认的管理员用户ID
        if ("admin".equals(token)) {
            UserContext.setUser(1L); // 设置默认管理员用户ID
            return true;
        }
        
        // 3.校验token
        Long userId = jwtTool.parseToken(token);
        // 4.存入上下文
        UserContext.setUser(userId);
        // 5.放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清理用户
        UserContext.removeUser();
    }
}
