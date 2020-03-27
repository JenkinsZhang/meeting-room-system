package com.jenkins.common.userservice.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;

@Component
public class MailUtil {

    private Logger logger = LoggerFactory.getLogger(MailUtil.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public int sendHtmlMail(String from,String to,String subject,String username,String url){
        try{
            Context context = new Context();
            context.setVariable("username",username);
            context.setVariable("url",url);
            String mail = templateEngine.process("mailtemplate.html", context);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(mail,true);
            javaMailSender.send(message);
            return 1;
        }catch (Exception e)
        {
            return -1;
        }

    }
}
