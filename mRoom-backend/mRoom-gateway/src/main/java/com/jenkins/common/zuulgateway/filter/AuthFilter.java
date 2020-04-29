package com.jenkins.common.zuulgateway.filter;

import com.alibaba.fastjson.JSON;
import com.jenkins.common.authinterface.model.UserInfo;
import com.jenkins.common.components.model.ResultVo;
import com.jenkins.common.zuulgateway.client.AuthClient;
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


/**
 * @author jenkinszhang
 */
@Component
@EnableConfigurationProperties(FilterProperties.class)
public class AuthFilter extends ZuulFilter {


    private FilterProperties filterProperties;

    private AuthClient authClient;

    @Autowired
    public AuthFilter(FilterProperties filterProperties, AuthClient authClient) {
        this.filterProperties = filterProperties;
        this.authClient = authClient;
    }

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
        String requestURI = request.getRequestURI();

        String token = request.getHeader("access-token");
        if (token == null) {
            response.setStatus(401);
            currentContext.setSendZuulResponse(false);
        } else {
            ResultVo resultVo = authClient.verifyToken(token);
            if(200 != resultVo.getCode())
            {
                currentContext.setSendZuulResponse(false);
                return null;
            }
            Object data = resultVo.getData();
            UserInfo userInfo = JSON.parseObject(JSON.toJSONString(data), UserInfo.class);
            System.out.println(userInfo);
            int roleID = userInfo.getRoleID();
            if(requestURI.contains("admin"))
            {
                if(roleID == 1)
                {
                    currentContext.setSendZuulResponse(false);
                    return null;
                }
                if(requestURI.contains("changeRole") && roleID != 3)
                {
                    currentContext.setSendZuulResponse(false);
                    return null;
                }
            }
        }
        return null;
    }
}
