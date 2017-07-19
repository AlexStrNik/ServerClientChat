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
    public static class ReplyMessage extends BasicRequest{
        public Items.Message message;
        public Items.Chat dst;
        public ReplyMessage(Items.Message msg, Items.Chat chat)
        {
            message=msg;
            dst=chat;
            type=ReplyMessage.class;
        }
        public ReplyMessage(){type=ReplyMessage.class;}
    }
    public static class SendMessage extends BasicRequest{
        public Items.Message message;
        public Items.Chat dst;
        public SendMessage(Items.Message msg, Items.Chat chat)
        {
            message=msg;
            dst=chat;
            type=SendMessage.class;
        }
        public SendMessage(){type=SendMessage.class;}
    }

}
