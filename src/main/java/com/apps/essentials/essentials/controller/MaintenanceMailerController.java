package com.apps.essentials.essentials.controller;

import com.apps.essentials.essentials.service.maintenance.CommandService;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import org.apache.commons.imaging.ImageReadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @author RiJAS
 * @Date 22-08-2024
 */


@RestController
@AllArgsConstructor
@RequestMapping("maintenance")
public class MaintenanceMailerController {


    @Autowired
    private CommandService commandService;


    @PostMapping("/sendMail")
    public ResponseEntity<?> sendMaintenanceEmail(@RequestParam(name = "receipt")MultipartFile multipartFile,
                                                  @RequestParam(name = "amount")Integer amount,
                                                  @RequestParam(name = "message")String message,
                                                  @RequestParam(name = "confirm", defaultValue = "N") String confirm) throws IOException, TemplateException, ImageReadException {
        Map<String, Object> data = commandService.processEmail(multipartFile, confirm, amount, message);
        return ResponseEntity.ok(data);
    }

}
