package com.company;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Никита on 17.07.2017.
 */
public class Client {
    private MessageListener messageListener;
    public void setMessageListener(MessageListener messageListener1)
    {
        this.messageListener=messageListener1;
    }
    public class ListenerThread extends Thread{
        ObjectInputStream inner;
        MessageListener messageListener;
        public ListenerThread(ObjectInputStream in,MessageListener messageListener1){
            this.inner=in;
            this.messageListener=messageListener1;
        }
        public void run() {
            REQUESTS.BasicRequest input;
            while (true){
                try {
                    input = (REQUESTS.BasicRequest) inner.readObject();
                    Object cd = input.type.cast(input);
                    if(cd instanceof REQUESTS.Message)
                    {
                        if(messageListener!=null)
                        {
                            messageListener.OnMessageRecived(((REQUESTS.Message) cd).Author+" say: "+((REQUESTS.Message) cd).Text);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public Client(){
        Socket fromserver = null;
        try {
            fromserver = new Socket();
            fromserver.connect(new InetSocketAddress("localhost",4040));
            out = new ObjectOutputStream(fromserver.getOutputStream());
            in  = new ObjectInputStream(fromserver.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void send(String message)
    {
        try {
            out.writeObject(new REQUESTS.Message(message,"UNKNOWN"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void start()
    {
        if(started)return;
        new ListenerThread(in,messageListener).start();
        started=true;
    }
    ObjectOutputStream out = null;
    public boolean started=false;
    ObjectInputStream in = null;
}
interface MessageListener{
    void OnMessageRecived(String message);
}