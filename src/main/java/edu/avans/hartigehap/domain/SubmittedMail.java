package edu.avans.hartigehap.domain;

public class SubmittedMail extends Mail {

	void setReceiver(String strReceiver) {
		System.out.println("Ik zet nu de ontvanger op: " + strReceiver);
	}

	void setSubject(String strSubject) {
		System.out.println("Zet het subject: " + strSubject);
	}

	void setBody(String strBody) {
		System.out.println("Zet de body: " + strBody);
	}

	void sent() {
		System.out.println("Ik verstuur het bericht nu.");
	}

}
