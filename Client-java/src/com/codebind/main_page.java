package com.codebind;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


import java.util.ArrayList;
import java.util.List;

public class main_page {
    JPanel panelMain;
    private JTextField id_field;
    private JLabel SCCTripsLabel;
    private JButton proposalButton;
    private JButton queryProposalButton;
    private JButton sendIntentButton;
    private JButton checkIntentButton;
    private JButton generateIDButton;
    private static JFrame frame = new JFrame("SCC Trips");
    private String id;
    private JTextArea messageList;
    private JTextField searchField;
    private JButton SearchAll;
    private JButton Search;

    private static List<String> proposals =new ArrayList<String>();

    public main_page(String id_param) {
        id = id_param;
        id_field.setText(id);

        proposalButton.addActionListener(e -> {
            frame.setVisible(false);
            submit_proposal_page page = new submit_proposal_page(frame);
            page.main(id_field.getText(), frame);
        });

        queryProposalButton.addActionListener(e -> {
            try {
                String txt = run_orchestrator.query_proposal_func();
                boolean unique_message = true;
                for (String proposal : proposals) {
                    if (proposal.equals(txt)) {
                        unique_message = false;
                        break;
                    }
                }
                if (unique_message) {
                    proposals.add(txt);
                    messageList.append(txt + "\n");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        generateIDButton.addActionListener(e -> {
            try {
                String id = run_orchestrator.get_id();
                id_field.setText(id);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        sendIntentButton.addActionListener(e -> {
            frame.setVisible(false);
            submit_intent page = new submit_intent(frame);
            page.main(id_field.getText(), frame);
        });

        checkIntentButton.addActionListener(e ->{
            try {
                String txt = run_orchestrator.check_intent(id_field.getText());
                messageList.append(txt);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        Search.addActionListener(e ->{
            messageList.setText("");
            for (String proposal : proposals) {
                if (proposal.contains(searchField.getText())){
                    messageList.append(proposal + "\n");
                };
            }
        });
        SearchAll.addActionListener(e ->{
            messageList.setText("");
            for (String proposal : proposals) {
                messageList.append(proposal + "\n");
            };
        });
    }

    public static void main(String[] args) {
        frame.setContentPane(new main_page("").panelMain);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setVisible(true);
    }

}