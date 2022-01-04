package com.codebind;

import javax.swing.*;
import java.io.IOException;

public class submit_intent {
    private JPanel Intent;
    private JButton SubmitIntentButton;
    private JTextField proposed_user_field;
    private JLabel proposed_user_label;
    private static String userID;


    public submit_intent(JFrame frame) {
        SubmitIntentButton.addActionListener(e -> {
            String proposed_user = proposed_user_field.getText();

            try {
                run_orchestrator.submit_intent(userID, proposed_user);
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
        frame.setContentPane(new submit_intent(frame).Intent);
        frame.pack();
        frame.setVisible(true);
    }
}


