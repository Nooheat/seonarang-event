package com.nooheat.seonarangevent.configurers;

import com.nooheat.seonarangevent.interceptors.JwtTokenRequired;
import com.nooheat.seonarangevent.interceptors.OnlyStreamer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class HttpInterceptorConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private JwtTokenRequired jwtTokenRequired;

    @Autowired
    private OnlyStreamer onlyStreamer;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenRequired)
//                .excludePathPatterns("/login");
                .addPathPatterns("/**")
                .excludePathPatterns("/callback");

        registry.addInterceptor(onlyStreamer)
                .addPathPatterns("/**")
                .excludePathPatterns("/callback");
    }
}
