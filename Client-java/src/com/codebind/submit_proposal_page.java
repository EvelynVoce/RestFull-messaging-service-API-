package com.codebind;

import javax.swing.*;
import java.io.IOException;

public class submit_proposal_page {

    private JPanel proposal;
    private JTextField date_field;
    private JTextField latitude_field;
    private JTextField longitude_field;
    private JLabel latitude_label;
    private JLabel date_label;
    private JButton submitProposalButton;
    private static String userID;


    public submit_proposal_page(JFrame frame) {
        submitProposalButton.addActionListener(e -> {
            String lat = latitude_field.getText();
            String lon = longitude_field.getText();
            String date = date_field.getText();

            try {
                run_orchestrator.submit_proposal(userID, lat, lon, date);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            frame.setVisible(false);
            frame.setContentPane(new main_page().panelMain);
            frame.pack();
            frame.setVisible(true);
        });
    }

    public static void main(String id, JFrame frame) {
        userID = id;
        frame.setContentPane(new submit_proposal_page(frame).proposal);
        frame.pack();
        frame.setVisible(true);
    }
}


