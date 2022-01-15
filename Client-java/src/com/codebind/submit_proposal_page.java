package com.codebind;

import javax.swing.*;
import java.io.IOException;

public class submit_proposal_page {

    private JPanel proposal;
    private JTextField date_field;
    private JTextField location_field;
    private JLabel date_label;
    private JButton submitProposalButton;
    private JLabel location_label;
    private static String userID;


    public submit_proposal_page(JFrame frame) {
        submitProposalButton.addActionListener(e -> {
            String location = location_field.getText();
            String date = date_field.getText();

            try {
                run_orchestrator.submit_proposal(userID, location, date);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            frame.setVisible(false);
            frame.setContentPane(new main_page(userID).panelMain);
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


