package mx.uady.sicei.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.from}")
    private String emailFromAdress;

    // @Async
    public String sendEmail(String body, String emailTo, String subject){
        String result = "El correo no fue enviado";
        try{
            // Create a mail sender
            result= "Enviando correo";
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(host);
            mailSender.setPort(port);
            mailSender.setUsername(username);
            mailSender.setPassword(password);

            // Create an email instance
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(this.emailFromAdress);
            mailMessage.setTo(emailTo);
            mailMessage.setSubject(subject);
            mailMessage.setText(body);

            // Send mail
            mailSender.send(mailMessage);
            result="Correo enviado";
        } catch(NullPointerException nullEx) {
            result = "Entró en la excepción!";
            System.out.println(nullEx.getMessage());
            System.out.println(host);
            System.out.println(port);
            System.out.println(username);
            System.out.println(password);
        }

        return result;
    }

}