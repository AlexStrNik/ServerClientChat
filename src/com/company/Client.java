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
        BufferedReader inner;
        MessageListener messageListener;
        public ListenerThread(BufferedReader in,MessageListener messageListener1){
            this.inner=in;
            this.messageListener=messageListener1;
        }
        public void run() {
            String input;
            while (true){
                try {
                    input = inner.readLine();
                    if(input!=null)
                    {
                        if(messageListener!=null)
                        {
                            messageListener.OnMessageRecived(input);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public Client(){
        Socket fromserver = null;
        try {
            fromserver = new Socket();
            fromserver.connect(new InetSocketAddress("https://obscure-river-98199.herokuapp.com/localhost",4040));
            in  = new BufferedReader(new InputStreamReader(fromserver.getInputStream()));
            out = new PrintWriter(fromserver.getOutputStream(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void send(String message)
    {
        out.println(message);
    }
    public void start()
    {
        if(started)return;
        new ListenerThread(in,messageListener).start();
        started=true;
    }
    PrintWriter out = null;
    public boolean started=false;
    BufferedReader in = null;
}
interface MessageListener{
    void OnMessageRecived(String message);
}