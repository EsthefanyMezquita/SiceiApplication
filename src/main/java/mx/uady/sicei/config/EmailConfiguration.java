package mx.uady.sicei.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailConfiguration{

    /*public EmailConfiguration(String host,int port,String username,String password){
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }*/
    @Value("${spring.mail.host}")
    private String host;

    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    public String getHost(){
        return host;
    }

    public void setHost(String host){
        this.host = host;
    }

    public int getPort(){
        return port;
    }
    
    public void setPort(int port){
        this.port = port;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
    
}