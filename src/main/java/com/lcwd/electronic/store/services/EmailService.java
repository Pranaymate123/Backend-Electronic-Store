package com.lcwd.electronic.store.services;

public interface EmailService {

    public boolean sendEmail(String subject,String message, String to);

}
