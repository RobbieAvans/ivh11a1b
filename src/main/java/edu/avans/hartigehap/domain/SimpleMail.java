package edu.avans.hartigehap.domain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleMail extends Mail {
    private String strMessage;
    private String strReceiver;
    private String strSubject;

    void setReceiver(String strReceiver) {
        log.debug("Zet de ontvanger op: " + strReceiver);
    }

    void setSubject(String strSubject) {
        log.debug("Zet het subject: " + strSubject);
    }

    void setBody(String strBody) {
    	strMessage = "Beste %voornaam%, de nieuwe status voor je reservering is: %status%. Met vriendelijke groet, team HH";
    	strMessage = strMessage.replaceAll("%status%", strBody);
    }

    void setFirstName(String strFirstname) {
        strMessage = strMessage.replaceAll("%voornaam%", strFirstname);
    }

    void sent() {
    	// Hier moet een mail object samengesteld worden en verstuurd worden
        log.debug("Ik verstuur onderstaand bericht nu.");
        log.debug(strMessage);
    }
}
