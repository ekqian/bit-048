package org.cis1200.game2048;

import java.io.*;
import java.util.*;

/**
 * This class implements the internal game model. The field variables include
 * the score, whether the player has made a move, a list of previous game
 * states, and the board, which is represented by a 2D integer array.
 */
public class GameModel {

    private int[][] board;
    private int score;
    private boolean playedTurn;
    private List<int[][]> gameStates = new LinkedList<>();

    // Sets up game state
    public GameModel() {
        reset();
    }

    // Returns a copy of current board
    public int[][] getBoard() {
        int[][] copyBoard = new int[4][4];
        for (int row = 0; row < board.length; row++) {
            System.arraycopy(board[row], 0, copyBoard[row], 0, board.length);
        }
        return copyBoard;
    }

    public void setBoard(int[][] newBoard) {
        for (int row = 0; row < board.length; row++) {
            System.arraycopy(newBoard[row], 0, board[row], 0, board.length);
        }
    }

    public int getScore() {
        return score;
    }

    // Recalculates the score based on current board
    public void setScore() {
        int newScore = 0;
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                int num = board[row][col];
                if (num != 0) {
                    newScore += num * ((Math.log(num) / Math.log(2)) - 1);
                }
            }
        }
        score = newScore;
    }

    // Helper functions that reorient board (used for moveRight, moveDown, and
    // moveUp)
    // -------------------------------------------------------

    private void flipHorizontal() {
        int length = board.length;
        int[][] newBoard = new int[length][length];
        for (int row = 0; row < length; row++) {
            for (int col = 0; col < length / 2; col++) {
                newBoard[row][col] = board[row][length - col - 1];
                newBoard[row][length - col - 1] = board[row][col];
            }
        }
        setBoard(newBoard);
    }

    private void rotateCW() {
        int length = board.length;
        int[][] newBoard = new int[length][length];
        for (int col = 0; col < length; col++) {
            for (int row = 0; row < length; row++) {
                newBoard[col][length - row - 1] = board[row][col];
            }
        }
        setBoard(newBoard);
    }

    private void rotateCCW() {
        int length = board.length;
        int[][] newBoard = new int[length][length];
        for (int col = 0; col < length; col++) {
            for (int row = 0; row < length; row++) {
                newBoard[length - col - 1][row] = board[row][col];
            }
        }
        setBoard(newBoard);
    }

    // Helper functions for moveRowLeft which combines and shifts a row to the left
    // -------------------------------------------------------
    private int findNextNonzero(int[] row, int index) {
        for (int i = index + 1; i < row.length; i++) {
            if (row[i] != 0) {
                return i;
            }
        }
        return row.length;
    }

    private void combineRowLeft(int[] row) {
        for (int i = 0; i < row.length; i++) {
            if (row[i] != 0) {
                int nextNonZero = findNextNonzero(row, i);
                if (nextNonZero != row.length && row[i] == row[nextNonZero]) {
                    row[i] *= 2;
                    row[nextNonZero] = 0;
                    score += row[i];
                    playedTurn = true;
                }
            }
        }
    }

    private void shiftRowLeft(int[] row) {
        for (int i = 0; i < row.length; i++) {
            if (row[i] == 0) {
                int nextNonzero = findNextNonzero(row, i);
                if (nextNonzero != row.length) {
                    row[i] = row[nextNonzero];
                    row[nextNonzero] = 0;
                    playedTurn = true;
                }
            }
        }
    }

    public void moveRowLeft(int[] row) {
        combineRowLeft(row);
        shiftRowLeft(row);
    }

    // Moves the board in left, right, down, and up
    // -------------------------------------------------------
    public void moveLeft() {
        for (int[] row : board) {
            moveRowLeft(row);
        }
    }

    public void moveRight() {
        flipHorizontal();
        moveLeft();
        flipHorizontal();
    }

    public void moveUp() {
        rotateCCW();
        moveLeft();
        rotateCW();
    }

    public void moveDown() {
        rotateCW();
        moveLeft();
        rotateCCW();
    }

    // Generates 2 in a random tile and adds the board to the list of game states if
    // the board changes
    // -------------------------------------------------------

    public void generateTwo() {
        if (playedTurn) {
            List<int[]> possiblePos = new ArrayList<int[]>();
            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board.length; col++) {
                    if (board[row][col] == 0) {
                        possiblePos.add(new int[] { row, col });
                    }
                }
            }

            int randomPos = (int) Math.floor(Math.random() * (possiblePos.size()));
            int[] position = possiblePos.get(randomPos);
            int row = position[0];
            int col = position[1];
            board[row][col] = 2;
        }
    }

    public void addState() {
        if (playedTurn) {
            int[][] board = getBoard();
            gameStates.add(board);
            playedTurn = false;
        }
    }

    // Checks if game has one, either by getting 2048 or having no more moves
    // -------------------------------------------------------

    private boolean has2048() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                if (board[row][col] == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canMoveHorizontal() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length - 1; col++) {
                if (board[row][col] == board[row][col + 1] || board[row][col] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canMoveVertical() {
        for (int row = 0; row < board.length - 1; row++) {
            for (int col = 0; col < board.length; col++) {
                if (board[row][col] == board[row + 1][col] || board[row][col] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkGameOver() {
        return has2048() || (!canMoveHorizontal() && !canMoveVertical());
    }

    public String displayMessage() {
        if (has2048()) {
            return "You Won!";
        } else {
            return "You Lose :(";
        }
    }

    // Implements the reset, save, load, and undo functions
    // -------------------------------------------------------

    private String boardToString(int[][] board) {
        String str = "";
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                str += board[row][col] + ",";
            }
        }
        str = str.substring(0, str.length() - 1);
        return str;
    }

    private int[][] stringToBoard(String str) {
        int[][] board = new int[4][4];
        String[] parse = str.split(",");
        for (int i = 0; i < parse.length; i++) {
            int num = Integer.parseInt(parse[i]);
            int row = (int) Math.floor(i / 4);
            int col = i % 4;
            board[row][col] = num;
        }
        return board;
    }

    public void reset() {
        board = new int[4][4];
        score = 0;
        playedTurn = true;
        gameStates.clear();
    }

    public void save() {
        try {
            File file = new File("./files/board.csv");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int[][] board : gameStates) {
                bw.write(boardToString(board));
                bw.newLine();
            }
            bw.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        } catch (IOException e) {
            System.out.println("Cannot Load File");
        }
    }

    public void load() {
        try {
            reset();
            File file = new File("./files/board.csv");
            Reader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                int[][] board = stringToBoard(line);
                gameStates.add(board);
                line = br.readLine();
            }
            br.close();

            board = gameStates.get(gameStates.size() - 1);
            setScore();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        } catch (IOException e) {
            System.out.println("Cannot Load File");
        }
    }

    public void undo() {
        if (gameStates.size() > 1) {
            gameStates.remove(gameStates.size() - 1);
            board = gameStates.get(gameStates.size() - 1);
            setScore();
        }
    }
}
