package edu.avans.hartigehap.domain;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public abstract class Mail{
	private static final long serialVersionUID = 1L;


	 public final void prepareMail(String strReceiver, String strSubject, String strMessage){
		 setReceiver(strReceiver);
		 setSubject(strSubject);
		 setBody(strMessage);
		 sent();
		 hook();
	 }
	 
	 abstract void setReceiver(String strReceiver);
	 abstract void setSubject(String strSubject);
	 abstract void setBody(String strMessage);
	 abstract void sent();
	 
	 void hook(){};

}
