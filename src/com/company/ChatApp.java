package com.company;


import com.notification.NotificationFactory;
import com.notification.manager.QueueManager;
import com.notification.types.TextNotification;
import com.theme.ThemePackagePresets;
import com.utils.Time;

import javax.swing.*;
import java.awt.*;
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
        client.user=new Items.User(JOptionPane.showInputDialog("Please input your nickname: "));
        NotificationFactory factory = new NotificationFactory(ThemePackagePresets.cleanDark());
        QueueManager plain = new QueueManager(NotificationFactory.Location.SOUTHEAST);
        plain.setScrollDirection(QueueManager.ScrollDirection.NORTH);
        JFrame guiFrame = new JFrame();
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("ChatGUI");
        guiFrame.setSize(300,250);
        guiFrame.setLocationRelativeTo(null);
        DefaultListModel<Items.Message> listModel = new DefaultListModel<Items.Message>();
        JList<Items.Message> list = new JList<Items.Message>(listModel);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setCellRenderer(new newGUI.BubbleRend());
        JScrollPane listScroller = new JScrollPane(list);
        client.setMessageListener(new MessageListener() {
            @Override
            public void OnMessageRecived(Items.Message message) {
                listModel.addElement(message);
                list.addNotify();
                list.ensureIndexIsVisible(listModel.getSize()-1);
                if(!message.user.nickname.equals(client.user.nickname)){
                    TextNotification notification = factory.buildTextNotification(message.user.nickname+" write new message: ", message.text);
                    notification.setCloseOnClick(true);
                    plain.addNotification(notification, Time.seconds(20));
                    Toolkit.getDefaultToolkit().beep();
                    if(plain.getNotifications().size()>10){
                        plain.removeNotification(plain.getNotifications().get(0));
                    }
                }
            }

            @Override
            public void OnMessageReplied(Items.Message message) {
                listModel.addElement(message);
                list.addNotify();
                list.ensureIndexIsVisible(listModel.getSize()-1);
                if(!message.user.nickname.equals(client.user.nickname)){
                    TextNotification notification = factory.buildTextNotification(message.user.nickname+" write new message: ", message.text);
                    notification.setCloseOnClick(true);
                    plain.addNotification(notification, Time.seconds(20));
                    Toolkit.getDefaultToolkit().beep();
                    if(plain.getNotifications().size()>10){
                        plain.removeNotification(plain.getNotifications().get(0));
                    }
                }
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
                    client.reply_message(listModel.get(index),textField.getText());
                    textField.setText("");
                    list.notifyAll();
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
