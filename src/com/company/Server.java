package com.company;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Никита on 17.07.2017.
 */
public class Server {
    public static void main(String[] args) {

        Server serv = new Server();
    }
    class AceptedThread extends Thread
    {
        Server serv;
        ServerSocket serverSocket = null;
        AceptedThread(Server main,ServerSocket main2)
        {
            this.serv=main;
            this.serverSocket=main2;
        }
        public void run()
        {
            while (true) {
                try {
                    Socket socket = null;
                    socket = serverSocket.accept();
                    ConnectionThread a = new ConnectionThread(socket,serv);
                    serv.conns.add(a);
                    a.start();
                } catch (IOException e) {
                    System.out.println("I/O error: " + e);
                }
                // new thread for a client

            }
        }
    }
    ArrayList<ConnectionThread> conns = new ArrayList<>();
    public Server()
    {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4040);
            System.out.print("Done"+"\n");
            System.out.print(InetAddress.getLocalHost().getHostName()+"\n");
            new AceptedThread(this,serverSocket).start();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}
class ConnectionThread extends Thread{
    private Socket socket;
    private Server server;
    private ObjectOutputStream out;
    ConnectionThread(Socket sock, Server serv)
    {
        this.server=serv;
        this.socket=sock;
    }
    public void send(REQUESTS.BasicRequest message) {
        if(out!=null)
        {
            try {
                out.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void run() {
        ObjectInputStream inp = null;
        out = null;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            inp = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            return;
        }
        REQUESTS.BasicRequest line;
        while (true) {
            try {
                line = (REQUESTS.BasicRequest) inp.readObject();
                Object cnv = (line.type.cast(line));
                if(cnv instanceof REQUESTS.SendMessage | cnv instanceof REQUESTS.ReplyMessage)
                {
                    for(ConnectionThread conn : server.conns)
                    {
                        conn.send((REQUESTS.BasicRequest) cnv);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}


