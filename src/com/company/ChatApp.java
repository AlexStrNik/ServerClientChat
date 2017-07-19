package com.company;


import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChatApp {

    public static void main(String[] args) {
        ChatApp app = new ChatApp();
    }

    private final JTextField textField;

    public ChatApp()
    {
        Client client = new Client();
        JFrame guiFrame = new JFrame();
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("ChatGUI");
        guiFrame.setSize(300,250);
        guiFrame.setLocationRelativeTo(null);
        DefaultListModel<String> listModel = new DefaultListModel<String>();
        JList<String> list = new JList<String>(listModel);
        list.setLayoutOrientation(JList.VERTICAL);
        JScrollPane listScroller = new JScrollPane(list);
        client.setMessageListener(new MessageListener() {
            @Override
            public void OnMessageRecived(Items.Message message) {
                listModel.addElement(message.user.nickname+" say: "+message.text);
                list.addNotify();
            }

            @Override
            public void OnMessageReplied(Items.Message message) {
                listModel.addElement(message.user.nickname+" reply: "+message.replied.text+"     \t\n"+message.text);
                list.addNotify();
            }
        });
        textField = new JTextField(20);
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                client.send_message(textField.getText(),new Items.Chat("l"));
                textField.setText("");
            }
        });
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                super.mouseClicked(evt);
                JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    client.reply_message(new Items.Message(listModel.get(index).split(" say: ")[1],new Items.User(listModel.get(index).split(" say: ")[0]),new Items.Chat("k")),textField.getText());
                    textField.setText("");
                }
            }
        });
        client.start();
        //guiFrame.add(list,BorderLayout.WEST);
        guiFrame.add(listScroller,BorderLayout.CENTER);
        guiFrame.add(textField,BorderLayout.SOUTH);
        guiFrame.setVisible(true);
    }

}
