package com.apps.essentials.essentials.service.maintenance;

import com.apps.essentials.essentials.config.MailProperties;
import com.apps.essentials.essentials.process.TesseractOCRProcess;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/**
 * @author RiJAS
 * @Date 21-05-2024
 */
@Service
@Slf4j
public class CommandService {

    @Autowired
    private Mailer mailer;

    @Autowired
    private TesseractOCRProcess tesseractOCRProcess;

    @Autowired
    private FTLProcessor ftlProcessor;

    @Autowired
    private DataExtractor dataExtractor;

    @Autowired
    private MailProperties mailProperties;



    public Map<String,Object> processEmail(MultipartFile file, String confirm, Integer amount, String message) throws IOException, TemplateException {
        Path tempFile = Path.of("D:\\images\\");
        if(Files.notExists(tempFile)){
            Files.createDirectories(tempFile);
        }
        File sourceImage = new File(tempFile+"\\"+file.getOriginalFilename());
        file.transferTo(sourceImage);
        return sendEmail(sourceImage,confirm, amount, message);
    }

    public Map<String,Object> sendEmail(File file, String confirm, Integer amount, String message) throws IOException, TemplateException {


        String dataFromImage = getDataFromImage(file, 4);

        Map<String, Object> dataModel = dataExtractor.processData(dataFromImage, amount, message);


        for(Map.Entry entry: dataModel.entrySet()){
            log.info(entry.getKey()+": "+ entry.getValue());
        }

        /*System.out.println("Are you sure? Y or N");
        Scanner scanner = new Scanner(System.in);
        String value = scanner.nextLine();*/

        log.info("Sending email....!");
        String htmlEmailMessage = ftlProcessor.getHTMLEmailMessage(dataModel);

        String messageFromImage = dataModel.get("message").toString();

        String fileName = dataModel.get("forMonth")+"_"+
                dataModel.get("paymentYear")+"_"+
                dataModel.get("transactionId")+".jpg";

        if(StringUtils.startsWithIgnoreCase(confirm,"Y")){
            log.info("Sending Email to {} ", mailProperties.getTo());

            mailer.sendHtmlEmailWithAttachment(mailProperties.getTo(), mailProperties.getCc(),
                    messageFromImage,htmlEmailMessage,file, fileName);
        }else {

            log.info("Sending Email to dummy {} ", mailProperties.getDummy());

            mailer.sendHtmlEmailWithAttachment(mailProperties.getDummy(), mailProperties.getCc(),
                    messageFromImage,htmlEmailMessage,file, fileName);

            log.info("Cancelled");
        }
        return dataModel;

    }




    public String getDataFromImage(File file, int psm) throws IOException {
        String recognizedText = tesseractOCRProcess.recognizeText(file, psm);
        return recognizedText;
    }






}
