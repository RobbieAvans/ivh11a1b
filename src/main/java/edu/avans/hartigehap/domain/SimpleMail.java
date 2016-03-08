package edu.avans.hartigehap.domain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleMail extends Mail {
    private String strMessage;

    @Override
    void setReceiver(String strReceiver) {
        // System.out.println("Ik zet nu de ontvanger op: " + strReceiver);
    }

    @Override
    void setSubject(String strSubject) {
        // System.out.println("Zet het subject: " + strSubject);
    }

    @Override
    void setBody(String strBody) {
        strMessage = strBody;
    }

    @Override
    void setFirstName(String strFirstname) {
        strMessage = strMessage.replaceAll("%voornaam%", strFirstname);
    }

    @Override
    void sent() {
        System.out.println("Ik verstuur onderstaand bericht nu.");
        System.out.println(strMessage);
    }

}
