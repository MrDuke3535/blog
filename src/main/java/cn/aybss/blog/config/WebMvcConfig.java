package cn.aybss.blog.config;

import cn.aybss.blog.web.interceptor.IndexInterceptor;
import cn.aybss.blog.web.interceptor.InstallInterceptor;
import cn.aybss.blog.web.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    public IndexInterceptor indexInterceptor;
    @Autowired
    public InstallInterceptor installInterceptor;
    @Autowired
    public LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(indexInterceptor);
        registry.addInterceptor(installInterceptor).addPathPatterns("/**").excludePathPatterns("/install").excludePathPatterns("/install/execute").excludePathPatterns("/js/**").excludePathPatterns("/css/**")
                .excludePathPatterns("/img/**").excludePathPatterns("/plugins/**");
        registry.addInterceptor(loginInterceptor).addPathPatterns("/admin/**").excludePathPatterns("/admin/login")
                .excludePathPatterns("/admin/getLogin");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/source/**").addResourceLocations("classpath:/templates/themes/");
        registry.addResourceHandler("/upload/**").addResourceLocations("classpath:/upload/")
                .addResourceLocations("file:///"+System.getProperties().getProperty("user.home")+"/blog/upload/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

}
