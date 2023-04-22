package com.tb.takeout.filter;

import com.alibaba.fastjson.JSON;
import com.tb.takeout.common.BaseContext;
import com.tb.takeout.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@ComponentScan
@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;

        String requestURI = request.getRequestURI();
        log.info("拦截到请求 {}",requestURI);
//        定义不需要处理的请求路径
        String[] urls=new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/sendMsg",
                "/user/login"
        };
        boolean check = check(urls, requestURI);
        if (check){
            log.info("拦截到不需要处理的请求请求 {}",requestURI);
            filterChain.doFilter(request,response);
            return;
        }
        if (request.getSession().getAttribute("employeeid")!=null){
            Long employeeid = (Long) request.getSession().getAttribute("employeeid");
            BaseContext.setId(employeeid);
            filterChain.doFilter(request,response);
            return;
        }
        if (request.getSession().getAttribute("user")!=null){
            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setId(userId);
            filterChain.doFilter(request,response);
            return;
        }
//        通过输出流向客户端写数据
//        response.getWriter().write() 功能：向前台页面显示一段信息。
//        当在普通的url方式中，会生成一个新的页面来显示内容。
//        当在ajax的方式中，会在alert中显示内容。
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }
    public boolean check(String[] urls,String requestURI){
        for (String url:urls){
            boolean match=PATH_MATCHER.match(url,requestURI);
            if (match){
                return true;
            }
        }
        return false;
    }
}
