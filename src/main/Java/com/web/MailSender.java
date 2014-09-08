package com.web;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MailSender {

    private String SMTP_HOST = "smtp.gmail.com";
    private String FROM_ADDRESS = "programmingTestsAndStuff@gmail.com";
    private String PASSWORD = "PasswordForTesting";
    private String FROM_NAME = "Kancho";

    public boolean sendMail(String recipient, String subject, String message, String attachment) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "false");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.enable", "true");
            Session session = Session.getInstance(props, new SocialAuth());
            Message msg = new MimeMessage(session);


            InternetAddress from = new InternetAddress(FROM_ADDRESS, FROM_NAME);
            msg.setFrom(from);
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));


            msg.setSubject(subject);
            Multipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(message, "text/html; charset=UTF-8");

            multipart.addBodyPart(messageBodyPart);
            if(attachment != null){
                MimeBodyPart attachPart = new MimeBodyPart();
                attachPart.attachFile(attachment);
                multipart.addBodyPart(attachPart);
            }
            msg.setContent(multipart);
            Transport.send(msg);
            return true;
            } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null, ex);
            return false;

            } catch (MessagingException ex) {
            Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    class SocialAuth extends Authenticator {

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {

            return new PasswordAuthentication(FROM_ADDRESS, PASSWORD);

            }
        }
}
