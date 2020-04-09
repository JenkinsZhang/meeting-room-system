package com.jenkins.common.roomService.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author JenkinsZhang
 * @date 2020/4/8
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:/Users/jenkinszhang/GRADUATION_PROJECT/meeting-room-system/mRoom-backend/mRoom-roomInfo/roomInfo-service/src/main/resources/static/");
    }
}
