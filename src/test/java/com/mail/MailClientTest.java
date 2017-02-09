package com.mail;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.mail.client.MailService;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(MailApplication.class)
public class MailClientTest {
 
    private GreenMail smtpServer;
    
    @Autowired
    private MailService mailClient;
 
    @Before
    public void setUp() throws Exception {
        smtpServer = new GreenMail(new ServerSetup(67, null, "smtp"));
        System.out.println("Starting..............................");
        smtpServer.start();
    }
    
    @Test
    public void shouldSendMail() throws Exception {
        //given
        String recipient = "name@bar.com";
        String message = "Test message content";
        //when
        mailClient.prepareAndSend(recipient, message);
        //then
        assertReceivedMessageContains(message);
    }
     
    private void assertReceivedMessageContains(String expected) throws IOException, MessagingException {
        MimeMessage[] receivedMessages = smtpServer.getReceivedMessages();
        Assert.assertEquals(1, receivedMessages.length);
        String content = (String) receivedMessages[0].getContent();
        Assert.assertTrue(content.contains(expected));
    }
    
    @Test
    public void test(){
    	System.out.println("Ankush");
    }
 
    @After
    public void tearDown() throws Exception {
    	System.out.println("Stropping....................");
        smtpServer.stop();
    }
     
}