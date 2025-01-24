//package com.apps.essentials.essentials.shell;
//
//import com.apps.essentials.essentials.service.maintenance.CommandService;
//import freemarker.template.TemplateException;
//import org.apache.commons.imaging.ImageReadException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.shell.standard.ShellComponent;
//import org.springframework.shell.standard.ShellMethod;
//import org.springframework.shell.standard.ShellOption;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Map;
//
///**
// * @author RiJAS
// * @Date 21-05-2024
// */
//
//@ShellComponent
//public class ShellCommands {
//
//    @Autowired
//    private CommandService commandService;
//
//    @ShellMethod(value = "Send Email", key = {"send-rent-mail"})
//    public Map<String, Object> sendRentEmail(@ShellOption(value = "file") String filePath) throws IOException, ImageReadException, TemplateException {
////        return commandService.processEmail(new File(filePath), "N");
//        return null;
//    }
//
//    @ShellMethod(value = "Process OCR", key = {"ocr"})
//    public String doOCR(@ShellOption(value = "file") String filePath, @ShellOption(value = "psm", defaultValue = "6") int psm) throws IOException, ImageReadException {
//        return commandService.getDataFromImage(new File(filePath), psm);
//    }
//
//}
