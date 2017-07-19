package com.company;


import java.io.Serializable;

/**
 * Created by Никита on 19.07.2017.
 */
public class REQUESTS {
    public static class BasicRequest implements Serializable
    {
     Class type;
    }

    public static class Message extends BasicRequest
    {
        String Text;
        String Author;
        public Message(String cText ,String cAuthor)
        {
            Text=cText;
            Author=cAuthor;
            type=Message.class;
        }
        public Message(){type= Message.class;}
    }

}
