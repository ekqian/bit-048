package org.cis1200.game2048;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

/**
 * This class sets up the top-level frame and widgets for the GUI. Game
 * initializes the view, connects the buttons with their functionalities,
 * and instantiates a GameBoard.
 */
public class Run2048 implements Runnable {
    public void run() {
        // Top-level frame containing game components
        final JFrame frame = new JFrame("2048");
        frame.setLocation(400, 100);

        // Pop-up instructions at start of game
        String message = "Welcome to 2048! \n \n To play the game, use the left, right, up, " +
                "and down arrow keys to move \n "
                + "the tiles. The goal of the game is to combine tiles " +
                "with the same \n number so that they reach 2048.";
        JOptionPane.showMessageDialog(frame, message, "Instructions", INFORMATION_MESSAGE);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("");
        status_panel.add(status);

        // Game board
        final GameBoard board = new GameBoard(status);
        frame.add(board, BorderLayout.CENTER);

        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Reset button
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> board.reset());
        control_panel.add(reset);

        // Save button
        final JButton save = new JButton("Save");
        save.addActionListener(e -> {
            try {
                board.save();
            } catch (IOException ex) {
                System.out.println("Cannot Load File");
            }
        });
        control_panel.add(save, BorderLayout.EAST);

        // Load button
        final JButton load = new JButton("Load");
        load.addActionListener(e -> {
            try {
                board.load();
            } catch (IOException ex) {
                System.out.println("Cannot Load File");
            }
        });
        control_panel.add(load, BorderLayout.EAST);

        // Undo button
        final JButton undo = new JButton("Undo");
        undo.addActionListener(e -> board.undo());
        control_panel.add(undo, BorderLayout.EAST);

        // Puts frame on screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Starts the game
        board.reset();
    }
}