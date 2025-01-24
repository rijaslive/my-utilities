package com.apps.essentials.essentials.service.maintenance;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author RiJAS
 * @Date 21-05-2024
 */
@Service
@Slf4j
public class FTLProcessor {
    @Autowired
    @Qualifier("HtmlConfig")
    private Configuration configuration;


    String getHTMLEmailMessage( Map<String, Object> dataModel) throws IOException, TemplateException {


        Template template = configuration.getTemplate("maitainance_email.ftl");
        StringWriter output = new StringWriter();
        template.process(dataModel, output);
        return output.toString();
    }




}
