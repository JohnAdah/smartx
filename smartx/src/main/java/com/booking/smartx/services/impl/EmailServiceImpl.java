package com.booking.smartx.services.impl;

import com.booking.smartx.services.EmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class EmailServiceImpl implements EmailService {

    private static Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    @Qualifier("customFreeMarkerConfig")
    private Configuration freemarkerConfig;


    @Override
    public void sendEmail(String recipient, String body, String subject) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(recipient);
            helper.setFrom("konetallrepo@gmail.com");
            helper.setSubject(subject);
            helper.setText(body, true);
            CompletableFuture.supplyAsync(()-> {
                this.javaMailSender.send(message);
                log.info("..........Message delivered to mail server..........");
                return 1;
            });
        } catch (MessagingException e) {
            log.error("Error while adding attachment to email, error is {}", e.getLocalizedMessage());
        }
    }


    public String prepareEmailTemplate(String template, Map<String, String> content){
        Template t = null;
        String defaultEmailBody = "";
        try{
            t = freemarkerConfig.getTemplate(template);
            defaultEmailBody = FreeMarkerTemplateUtils.processTemplateIntoString(t,content);
        } catch (IOException | TemplateException e){
            log.error("{}", e.getMessage());
        }
        return defaultEmailBody;
    }
}
