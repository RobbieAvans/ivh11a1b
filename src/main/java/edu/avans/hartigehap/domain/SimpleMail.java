package edu.avans.hartigehap.domain;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleMail extends Mail {
    private String strMessage;
    private String strReceiver;
    private String strSubject;

    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;

    void setReceiver(String strReceiver) {
        this.strReceiver = strReceiver;
        log.debug("Zet de ontvanger op: " + strReceiver);
    }

    void setSubject(String strSubject) {
        this.strSubject = strSubject;
        log.debug("Zet het subject: " + strSubject);
    }

    void setBody(String strBody) {
        strMessage = "Beste %voornaam%,<br><br> De nieuwe status voor je reservering is: %status%.<br><br> Met vriendelijke groet,<br> Team HH";
        strMessage = strMessage.replaceAll("%status%", strBody);
    }

    void setFirstName(String strFirstname) {
        strMessage = strMessage.replaceAll("%voornaam%", strFirstname);
    }

    void sent() {
        // Hier moet een mail object samengesteld worden en verstuurd worden

        log.debug("\n 1st ===> setup Mail Server Properties..");
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        log.debug("Mail Server Properties have been setup successfully..");
        try {
            log.debug("\n\n 2nd ===> get Mail Session..");
            getMailSession = Session.getDefaultInstance(mailServerProperties, null);
            generateMailMessage = new MimeMessage(getMailSession);

            generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(strReceiver));
            generateMailMessage.setSubject(strSubject);
            String emailBody = strMessage;
            generateMailMessage.setContent(emailBody, "text/html");
            log.debug("Mail Session has been created successfully..");

            log.debug("\n\n 3rd ===> Get Session and Send mail");
            Transport transport = getMailSession.getTransport("smtp");

            transport.connect("smtp.gmail.com", "ivh11a1b@gmail.com", "wwivh11a1b");
            transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
            transport.close();
        } catch (AddressException e) {
            log.debug("AddressException",e);
        } catch (MessagingException e) {
            log.debug("MessagingException",e);
        }
    }
}
