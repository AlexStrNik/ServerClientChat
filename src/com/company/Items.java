package com.company;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Никита on 19.07.2017.
 */
public class Items {
    public static class Message implements Serializable
    {
        String text;
        User user;
        Message replied;
        Chat chat;
        Message[] forwarded;
        public Message(String cText ,User cUser)
        {
            text=cText;
            user = cUser;
        }
        public Message(String cText ,User cUser, Chat chat1)
        {
            text=cText;
            user = cUser;
            chat = chat1;
        }
        public Message Reply(Message main)
        {
            main.replied=this;
            return main;
        }
        public Message Forward(Message[] another)
        {
            this.forwarded=another;
            return this;
        }
        public Message(){}
    }
    public static class User implements Serializable{
        String nickname;
        String password;
        public User(String name)
        {
            nickname=name;
        }
        public User(){}
    }
    public static class permission implements Serializable{
        boolean canSend;
        boolean canRead;
        public static permission ALL()
        {
            return new permission(true,true);
        }
        public static permission READ()
        {
            return new permission(false,true);
        }
        public static permission WRITE()
        {
            return new permission(true,false);
        }
        public static permission NOTHING()
        {
            return new permission(false,false);
        }
        public permission(){}
        public permission(boolean pSend,boolean pRead){
            canRead=pRead;
            canSend=pSend;
        }
    }
    public static class Chat implements Serializable{
        public Chat(){}
        public Chat(String cname)
        {
            name=cname;
        }
        public void AddUser(User user)
        {
            users.put(user,permission.ALL());
        }
        public void BanUser(User user,permission perm)
        {
            users.replace(user,perm);
        }
        public void KickUser(User user)
        {
            users.remove(user);
        }
        String name;
        HashMap<User,permission> users = new HashMap<User,permission>();
    }
}
