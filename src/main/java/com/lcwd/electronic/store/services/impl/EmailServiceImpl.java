package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.services.EmailService;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {
    @Override
    public boolean sendEmail(String subject, String message, String to) {
        boolean flag=false;

        //variable for gmail
        String host="smtp.gmail.com";

        String from="pranaymate0706@gmail.com";
        //get System properties
        Properties properties=System.getProperties();

        System.out.println("Properties" +properties);

        //setting important information to property object
        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.auth","true");


        // step 1: To get the session object
        Session session=Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("pranaymate0706@gmail.com","kfjs pmfm mpxf phwi");
            }
        });

        session.setDebug(true);

        ///step 2: compose the message [text ,multimedia]
        MimeMessage m=new MimeMessage(session);

        try{
            //from email
            m.setFrom(from);

            //adding recipeint to message
            m.addRecipient(Message.RecipientType.TO ,new InternetAddress(to));

            // adding subject to the message
            m.setSubject(subject);

            //adding text to message
            m.setContent(message,"text/html");

            // send messgae using Transport Class
            Transport.send(m);

            System.out.println("Mail Sent SuccessFully ");
            flag=true;

        }catch(Exception e)
        {
            e.printStackTrace();

        }

        return flag;
    }
}
