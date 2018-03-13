package com.optimum.dao;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EmailSupport {
	final String username = "fhhan101@gmail.com";
    final String password = "block881";

    public void SendFromGMail(String toEmail, String toName, String Password ) {
    	Properties props = new Properties();
    	props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //SSL factory class
        props.put("mail.smtp.auth", "true");       
        props.put("mail.smtp.port", "465"); //SSL port

        
        
        
        
        Session session = Session.getInstance(props,
          new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(toEmail));
            message.setSubject("Account Registration - Login System");
            message.setText("Dear " + toName + ","
                + "\n\n An adminstrator has a registered for you a new account for the login system. The login id will be the email address which you received this email from, "
                + "and the password is " + Password + "\n\n Best Regards," + "\n Login System Pc");

            Transport.send(message);

            System.out.println("...and an email containing login details has been sent to the user's email address.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
 
}
