package com.company;


import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
            public void OnMessageRecived(String message) {
                listModel.addElement(message);
                list.addNotify();
            }
        });
        textField = new JTextField(20);
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                client.send(textField.getText());
            }
        });
        client.start();
        guiFrame.add(list,BorderLayout.WEST);
        guiFrame.add(textField,BorderLayout.SOUTH);
        guiFrame.setVisible(true);
    }

}
