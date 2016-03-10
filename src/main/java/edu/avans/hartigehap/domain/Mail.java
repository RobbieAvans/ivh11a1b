package edu.avans.hartigehap.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Mail {

    public final void prepareMail(String strReceiver, String strSubject, String strMessage, String strFirstName) {
        setReceiver(strReceiver);
        setSubject(strSubject);
        setBody(strMessage);
        setFirstName(strFirstName);
        sent();
        hook();
    }

    abstract void setReceiver(String strReceiver);

    abstract void setSubject(String strSubject);

    abstract void setBody(String strMessage);

    abstract void setFirstName(String strFirstName);

    abstract void sent();

    void hook(){
        
    };

}
