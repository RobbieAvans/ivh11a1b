package edu.avans.hartigehap.domain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleMail extends Mail {
	private String strMessage;
	
	void setReceiver(String strReceiver) {
		log.debug("Zet de ontvanger op: " + strReceiver);
	}

	void setSubject(String strSubject) {
		log.debug("Zet het subject: " + strSubject);
	}

	void setBody(String strBody) {
		strMessage = strBody;
	}
	
	void setFirstName(String strFirstname) {
		strMessage = strMessage.replaceAll("%voornaam%", strFirstname);
	}

	void sent() {
		log.debug("Ik verstuur onderstaand bericht nu.");
		log.debug(strMessage);
	}

}
