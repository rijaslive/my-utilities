package com.apps.essentials.essentials.controller;

import com.apps.essentials.essentials.service.maintenance.CommandService;
import com.apps.essentials.essentials.service.pdf.PdfCreator;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import org.apache.commons.imaging.ImageReadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * @author RiJAS
 * @Date 22-08-2024
 */


@RestController
@AllArgsConstructor
@RequestMapping("pdf")
public class PDFController {


    @Autowired
    private PdfCreator pdfCreator;


    @PostMapping("/generate")
    public ResponseEntity<?> generatePDF(@RequestParam(name = "input")MultipartFile multipartFile) throws IOException, TemplateException, ImageReadException {
        ByteArrayOutputStream outputStream = pdfCreator.generatePDF(multipartFile);
        byte[] pdfBytes = outputStream.toByteArray();
        ByteArrayResource inputStreamResource = new ByteArrayResource(pdfBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + multipartFile.getOriginalFilename().replace("txt","pdf") + "\"");
        return ResponseEntity.ok().headers(headers).body(inputStreamResource);
    }

}
