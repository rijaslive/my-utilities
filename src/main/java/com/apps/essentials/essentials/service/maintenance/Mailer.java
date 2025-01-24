package com.apps.essentials.essentials.service.maintenance;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * @author RiJAS
 * @Date 21-05-2024
 */
@Component
public class Mailer {

    @Autowired
    private JavaMailSender mailSender;

    public void sendHtmlEmailWithAttachment(List<String> to,List<String> cc,
                                            String subject, String htmlBody,
                                            File file, String fileName) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to.toArray(new String[to.size()]));
            if(CollectionUtils.isNotEmpty(cc)){
                helper.setCc(cc.toArray(new String[cc.size()]));
            }
            helper.setTo(to.toArray(new String[to.size()]));
            helper.setSubject(subject);
            helper.setText(htmlBody, true);

            // Add attachment
            if (file != null) {
                helper.addAttachment(fileName, file);
            }
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
