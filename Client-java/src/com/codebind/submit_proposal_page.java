package com.codebind;

import javax.swing.*;
import java.awt.*;

public class submit_proposal_page {

    private JTextField date_field;
    private JTextField location_field;
    private JLabel location_label;
    private JLabel date_label;
    private JButton submitProposalButton;
    JPanel proposal;

    public static void main() {
        JFrame frame = new JFrame("SCC Trips");
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setContentPane(new submit_proposal_page().proposal);
        frame.pack();
        frame.setVisible(true);
    }
}


