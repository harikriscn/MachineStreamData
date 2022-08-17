package com.zeiss.websocket;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.websocket.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@ClientEndpoint
public class WebSocketClient {
    private static Object waitLock = new Object();

    private int count =0;
    public static List<String> messages = new ArrayList<>();

    private static JmsTemplate jmsTemplate;

    @OnMessage
    public void onMessage(String message) {
        System.out.println("Received msg: "+message);
       /* List<String> messages = new ArrayList<>();
        messages.add(message);*/
        jmsTemplate.send("messages",new MessageCreator() {
            @Override
            public Message createMessage(javax.jms.Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });

    }

    @OnOpen
    public void onOpen( Session userSession ) {
        System.out.println( );System.out.println( "opened connection" + userSession);
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println( "closed connection : " + reason + "on session : " + userSession);
        System.out.println( "Session is closed :" +!userSession.isOpen());
        retry(userSession, messages);
    }

    private void retry(Session userSession, List<String> messages) {
        count++;
        if(!userSession.isOpen()) {
            //webSocketClientService.writeInToRepository();
            messages.clear();
            System.out.println( "Recreate session and connect to server count : "+ count);
            createSessionAndConnectToserver(this.jmsTemplate);
        }
    }

    private static void  wait4TerminateSignal()
    {
        synchronized(waitLock)
        {try {
            waitLock.wait();
        } catch (InterruptedException e) {
        }}}


    public void createSessionAndConnectToserver(JmsTemplate jmsTemplate) {
        WebSocketContainer container=null;//
        Session session=null;
        try{
            container = ContainerProvider.getWebSocketContainer();
            session=container.connectToServer(WebSocketClient.class, URI.create("ws://machinestream.herokuapp.com/ws"));
            this.jmsTemplate = jmsTemplate;
            wait4TerminateSignal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if(session!=null){
                try {
                    System.out.println("Session is closing");
                    session.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
