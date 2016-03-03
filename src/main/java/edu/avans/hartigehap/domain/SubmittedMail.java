package edu.avans.hartigehap.domain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubmittedMail extends Mail {

	void setReceiver(String strReceiver) {
		log.debug("Ik zet nu de ontvanger op: " + strReceiver);
	}

	void setSubject(String strSubject) {
		log.debug("Zet het subject: " + strSubject);
	}

	void setBody(String strBody) {
		log.debug("Zet de body: " + strBody);
	}

	void sent() {
		log.debug("Ik verstuur het bericht nu.");
	}

}
