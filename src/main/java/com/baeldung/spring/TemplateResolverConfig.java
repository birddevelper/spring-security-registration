package com.baeldung.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class TemplateResolverConfig {

    // Here the second place for view templates is added
    @Bean
    public ClassLoaderTemplateResolver secondaryTemplateResolver() {
        ClassLoaderTemplateResolver privateTemplateResolver = new ClassLoaderTemplateResolver();
        privateTemplateResolver.setPrefix("private/");
        privateTemplateResolver.setSuffix(".html");
        privateTemplateResolver.setTemplateMode(TemplateMode.HTML);
        privateTemplateResolver.setCharacterEncoding("UTF-8");
        privateTemplateResolver.setOrder(1);
        privateTemplateResolver.setCheckExistence(true);
        return privateTemplateResolver;
    }
}
