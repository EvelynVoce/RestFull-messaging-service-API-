package com.codebind;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class main_page {
    private JButton proposalButton;
    private JPanel panelMain;
    private JTextField id_field;
    private JLabel SCCTripsLabel;
    private JButton queryProposalButton;
    private JButton sendIntentButton;
    private JButton checkIntentButton;
    private JButton generateIDButton;
    private static JFrame frame = new JFrame("SCC Trips");


    public main_page() {
        proposalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("hello world! U just pressed my buttons");
                frame.setVisible(false);
                submit_proposal_page page = new submit_proposal_page();
                page.main();
            }
        });

        queryProposalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        generateIDButton.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    String id = run_orchestrator.main();
                    id_field.setText(id);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        frame.setContentPane(new main_page().panelMain);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setVisible(true);
    }

    public static class get_response {
        public static String main(String url_str) throws IOException {
            URL url = new URL(url_str);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            BufferedReader reader;
            String line;
            StringBuilder responseContent = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            con.disconnect();
            return responseContent.toString();
        }
    }
}