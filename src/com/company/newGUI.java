package com.company;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by admin on 16.08.2017.
 */
public class newGUI {
    static class BubbleRend extends JLabel implements ListCellRenderer<Object> {
        public BubbleRend() {
            setOpaque(true);
        }

        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

            value = (Items.Message)value;
            JEditorPane jEditorPane = new JEditorPane();
            jEditorPane.setEditable(false);
            HTMLEditorKit kit = new HTMLEditorKit();
            jEditorPane.setEditorKit(kit);
            StyleSheet styleSheet = kit.getStyleSheet();
            String initialText = "<html>\n" +
                    ((Items.Message) value).user.nickname+" say:\n";
            setText(((Items.Message) value).text);
            if(((Items.Message) value).replied!=null){
                initialText+=
                        "<ul>" +
                        "<li><font color=green>"+((Items.Message) value).replied.user.nickname+" say: "+((Items.Message) value).replied.text+"</font>\n" +
                        "</ul>\n";
            }
            initialText+=((Items.Message) value).text;
            setText(initialText);
            Color background;
            Color foreground;

            // check if this cell represents the current DnD drop location
            JList.DropLocation dropLocation = list.getDropLocation();
            if (dropLocation != null
                    && !dropLocation.isInsert()
                    && dropLocation.getIndex() == index) {

                background = Color.BLUE;
                foreground = Color.WHITE;

                // check if this cell is selected
            } else if (isSelected) {
                background = Color.RED;
                foreground = Color.WHITE;

                // unselected, and not the DnD drop location
            } else {
                background = Color.WHITE;
                foreground = Color.BLACK;
            };

            setBackground(background);
            setForeground(foreground);

            return this;
        }
    }
}


