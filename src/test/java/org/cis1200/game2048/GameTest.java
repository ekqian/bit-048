package org.cis1200.game2048;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * You can use this file (and others) to test your
 * implementation.
 */

public class GameTest {

    /* Test MoveRowLeft */
    // -------------------------------------------------------

    @Test
    public void testMoveRowLeftZeroRow() {
        GameModel gm = new GameModel();
        int[] emptyRow = { 0, 0, 0, 0 };
        gm.moveRowLeft(emptyRow);
        assertArrayEquals(new int[4], emptyRow);
    }

    @Test
    public void testMoveRowLeftCombineAndShift() {
        GameModel gm = new GameModel();
        int[] row = { 0, 2, 2, 2 };
        gm.moveRowLeft(row);
        assertArrayEquals(new int[] { 4, 2, 0, 0 }, row);
    }

    @Test
    public void testMoveRowLeftCombineOnce() {
        GameModel gm = new GameModel();
        int[] row = { 0, 2, 2, 4 };
        gm.moveRowLeft(row);
        assertArrayEquals(new int[] { 4, 4, 0, 0 }, row);
    }

    @Test
    public void testMoveRowLeftNoChange() {
        GameModel gm = new GameModel();
        int[] row = { 2, 4, 2, 4 };
        gm.moveRowLeft(row);
        assertArrayEquals(new int[] { 2, 4, 2, 4 }, row);
    }

    /* Test MoveLeft, MoveRight, MoveUp, and MoveDown */
    // -------------------------------------------------------

    @Test
    public void testMoveLeft() {
        GameModel gm = new GameModel();
        int[][] board = new int[][] {
            { 0, 2, 2, 4 },
            { 2, 4, 8, 4 },
            { 2, 2, 2, 0 },
            { 0, 0, 0, 0 } };
        int[][] newBoard = new int[][] {
            { 4, 4, 0, 0 },
            { 2, 4, 8, 4 },
            { 4, 2, 0, 0 },
            { 0, 0, 0, 0 } };
        gm.setBoard(board);
        gm.moveLeft();
        assertArrayEquals(gm.getBoard(), newBoard);
    }

    @Test
    public void testMoveRight() {
        GameModel gm = new GameModel();
        int[][] board = new int[][] {
            { 0, 2, 2, 4 },
            { 2, 4, 8, 4 },
            { 2, 2, 2, 0 },
            { 0, 0, 0, 0 } };
        int[][] newBoard = new int[][] {
            { 0, 0, 4, 4 },
            { 2, 4, 8, 4 },
            { 0, 0, 2, 4 },
            { 0, 0, 0, 0 } };
        gm.setBoard(board);
        gm.moveRight();
        assertArrayEquals(gm.getBoard(), newBoard);
    }

    @Test
    public void testMoveUp() {
        GameModel gm = new GameModel();
        int[][] board = new int[][] {
            { 0, 2, 2, 4 },
            { 2, 4, 8, 4 },
            { 2, 2, 2, 4 },
            { 0, 0, 0, 0 } };
        int[][] newBoard = new int[][] {
            { 4, 2, 2, 8 },
            { 0, 4, 8, 4 },
            { 0, 2, 2, 0 },
            { 0, 0, 0, 0 } };
        gm.setBoard(board);
        gm.moveUp();
        assertArrayEquals(gm.getBoard(), newBoard);
    }

    @Test
    public void testMoveDown() {
        GameModel gm = new GameModel();
        int[][] board = new int[][] {
            { 0, 2, 2, 4 },
            { 2, 4, 8, 4 },
            { 2, 2, 2, 4 },
            { 0, 0, 0, 0 } };
        int[][] newBoard = new int[][] {
            { 0, 0, 0, 0 },
            { 0, 2, 2, 0 },
            { 0, 4, 8, 4 },
            { 4, 2, 2, 8 } };
        gm.setBoard(board);
        gm.moveDown();
        assertArrayEquals(gm.getBoard(), newBoard);
    }

    /* Test checkGameOver */
    // -------------------------------------------------------

    @Test
    public void testGameOverNoMoves() {
        GameModel gm = new GameModel();
        int[][] board = new int[][] {
            { 2, 4, 2, 4 },
            { 4, 2, 8, 2 },
            { 2, 4, 2, 4 },
            { 8, 2, 4, 2 } };
        gm.setBoard(board);
        assertTrue(gm.checkGameOver());
    }

    @Test
    public void testGameOver2048() {
        GameModel gm = new GameModel();
        int[][] board = new int[][] {
            { 0, 2, 2, 4 },
            { 2, 4, 8, 4 },
            { 2, 2, 2, 4 },
            { 0, 0, 0, 2048 } };
        gm.setBoard(board);
        assertTrue(gm.checkGameOver());
    }

    @Test
    public void testNotGameOver() {
        GameModel gm = new GameModel();
        int[][] board = new int[][] {
            { 0, 2, 2, 4 },
            { 2, 4, 8, 4 },
            { 2, 2, 2, 4 },
            { 0, 0, 0, 0 } };
        gm.setBoard(board);
        assertFalse(gm.checkGameOver());
    }

    /* Test setScore */
    // -------------------------------------------------------

    @Test
    public void testSetScoreZero() {
        GameModel gm = new GameModel();
        int[][] board = new int[][] {
            { 0, 0, 2, 2 },
            { 2, 0, 0, 2 },
            { 2, 2, 2, 2 },
            { 0, 0, 0, 0 } };
        gm.setBoard(board);
        gm.setScore();
        assertEquals(0, gm.getScore());
    }

    @Test
    public void testSetScore() {
        GameModel gm = new GameModel();
        int[][] board = new int[][] {
            { 0, 0, 2, 2 },
            { 2, 4, 0, 2 },
            { 2, 2, 2, 2 },
            { 0, 8, 0, 0 } };
        gm.setBoard(board);
        gm.setScore();
        assertEquals(20, gm.getScore());
    }

    /* Test reset and undo */
    // -------------------------------------------------------

    @Test
    public void testReset() {
        GameModel gm = new GameModel();
        int[][] board = new int[][] {
            { 0, 0, 2, 2 },
            { 2, 0, 0, 2 },
            { 2, 2, 2, 2 },
            { 0, 0, 0, 0 } };
        gm.setBoard(board);
        gm.moveUp();
        gm.moveRight();
        gm.reset();
        assertEquals(gm.getScore(), 0);
        assertArrayEquals(gm.getBoard(), new int[4][4]);
    }

    @Test
    public void testUndo() {
        GameModel gm = new GameModel();
        int[][] board = new int[][] {
            { 0, 0, 2, 2 },
            { 2, 0, 0, 2 },
            { 2, 2, 2, 2 },
            { 0, 0, 0, 0 } };
        gm.setBoard(board);
        gm.addState();
        gm.moveRight();
        gm.addState();
        gm.moveUp();
        gm.addState();
        gm.undo();
        gm.undo();
        assertEquals(gm.getScore(), 0);
        assertArrayEquals(gm.getBoard(), board);
    }
}
