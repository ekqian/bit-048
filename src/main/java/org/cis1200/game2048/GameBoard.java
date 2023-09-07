package org.cis1200.game2048;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * This class instantiates a game model for 2048. It serves as both the
 * controller and the view since the player can make a move by pressing an
 * arrow key or pressing one of the buttons. The board repaints itself everytime
 * the board changes.
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private GameModel gm; // Game mode
    private JLabel status; // Status text

    // Game constants
    public static final int BOARD_WIDTH = 500;
    public static final int BOARD_HEIGHT = 550;

    // Initializes the game board.
    public GameBoard(JLabel statusInit) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setFocusable(true);

        gm = new GameModel(); // Initializes model for the game
        status = statusInit; // Initializes the status JLabel

        // Listens for key inputs and moves the board in one of the four directions
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    gm.moveLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    gm.moveRight();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    gm.moveDown();
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    gm.moveUp();
                }
                gm.generateTwo();
                gm.addState();
                updateStatus();
                repaint();
            }
        });
    }

    // Resets game to initial state
    public void reset() {
        gm.reset();
        gm.generateTwo();
        gm.addState();
        repaint();
        status.setText("");
        requestFocusInWindow();
    }

    // Saves game to board.csv
    public void save() throws IOException {
        gm.save();
        requestFocusInWindow();
    }

    // Loads game state from board.csv
    public void load() throws IOException {
        gm.load();
        repaint();
        requestFocusInWindow();
    }

    // Undoes move by changing to the previous state
    public void undo() {
        gm.undo();
        repaint();
        requestFocusInWindow();
    }

    // Updates the JLabel to reflect the current state of the game.
    private void updateStatus() {
        if (gm.checkGameOver()) {
            String endMessage = gm.displayMessage();
            status.setText(endMessage);
        }
    }

    // Draws game board
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws board grid and sets font size
        g.drawLine(125, 0, 125, 500);
        g.drawLine(250, 0, 250, 500);
        g.drawLine(375, 0, 375, 500);
        g.drawLine(0, 125, 500, 125);
        g.drawLine(0, 250, 500, 250);
        g.drawLine(0, 375, 500, 375);

        // Displays score
        g.drawLine(0, 500, 500, 500);
        String score = Integer.toString(gm.getScore());
        g.drawString("Score: " + gm.getScore(), 225, 525);

        // Draws tiles
        int[][] board = gm.getBoard();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                String num = Integer.toString(board[row][col]);
                if (!num.equals("0")) {
                    if (num.length() == 1) {
                        g.setFont(g.getFont().deriveFont(60.0f));
                        g.drawString(num, (int) (45 + 125 * col), (int) (82.5 + 125 * row));
                    } else if (num.length() == 2) {
                        g.setFont(g.getFont().deriveFont(60.0f));
                        g.drawString(num, (int) (25 + 125 * col), (int) (82.5 + 125 * row));
                    } else if (num.length() == 3) {
                        g.setFont(g.getFont().deriveFont(48.0f));
                        g.drawString(num, (int) (17.5 + 125 * col), (int) (80 + 125 * row));
                    } else {
                        g.setFont(g.getFont().deriveFont(40.0f));
                        g.drawString(num, (int) (15 + 125 * col), (int) (77.5 + 125 * row));
                    }
                }
            }
        }
    }

    // Returns the size of the game board.
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
