package com.codebind;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class test {
    private JButton clickHereButton;
    private JPanel panelMain;
    private JTextField id_field;
    private JLabel SCCTripsLabel;
    private JButton queryProposalButton;
    private JButton sendIntentButton;
    private JButton checkIntentButton;
    private JButton generateIDButton;


    public test() {
        clickHereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("hello world! U just pressed my buttons");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("SCC Trips");
        frame.setContentPane(new test().panelMain);
        frame.pack();
        frame.setVisible(true);
    }
}