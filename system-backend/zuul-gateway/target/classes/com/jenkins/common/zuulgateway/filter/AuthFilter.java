package com.jenkins.common.zuulgateway.filter;

import com.jenkins.common.authservice.model.UserInfo;
import com.jenkins.common.authservice.utils.JwtUtil;
import com.jenkins.common.zuulgateway.config.FilterProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Component
@EnableConfigurationProperties(FilterProperties.class)
public class AuthFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private FilterProperties filterProperties;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 5;
    }

    @Override
    public boolean shouldFilter() {
        List<String> allowPaths = filterProperties.getAllowPaths();
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String requestURI = request.getRequestURI();
        for (String allowPath : allowPaths) {
            if (requestURI.startsWith(allowPath)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpServletResponse response = currentContext.getResponse();
        String token = request.getHeader("access-token");
        if (token == null) {
            response.setStatus(401);
            currentContext.setSendZuulResponse(false);
        } else {
            UserInfo userInfo = jwtUtil.verifyToken(token, response);
            if (userInfo == null) {
                currentContext.setSendZuulResponse(false);
            }
        }
        return null;
    }
}
