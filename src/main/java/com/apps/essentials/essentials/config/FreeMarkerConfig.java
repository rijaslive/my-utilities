package com.apps.essentials.essentials.config;

import freemarker.cache.ClassTemplateLoader;
import freemarker.core.HTMLOutputFormat;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class FreeMarkerConfig {

    @Bean(name = "HtmlConfig")
    public freemarker.template.Configuration freemarkerHtmlConfig() throws IOException {
        freemarker.template.Configuration configuration = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_31);
        configuration.setDefaultEncoding("UTF-8"); // Set the default encoding
        configuration.setTemplateLoader(new ClassTemplateLoader(getClass(), "/templates"));
        configuration.setOutputFormat(HTMLOutputFormat.INSTANCE); // Set HTML output format
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER); // Set the exception handler
        // Add more configuration if needed
        return configuration;
    }
}
