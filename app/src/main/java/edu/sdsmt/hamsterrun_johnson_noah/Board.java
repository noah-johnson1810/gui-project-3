package edu.sdsmt.hamsterrun_johnson_noah;

/* Author: Noah Johnson
 * Class: CSC 468 - GUI Programming
 * Description: Board Class for Hamster Run Project
 */

import java.util.ArrayList;


public class Board {

    // private variables
    private ArrayList<Square> board = new ArrayList<>();

    /* Description: Constructor for board class
     */

    Board() {
        // row 0
        board.add(new Square("tube"));
        board.add(new Square("tube"));
        board.add(new Square("bars"));
        board.add(new Square("food", 10));
        board.add(new Square("tube"));

        // row 1
        board.add(new Square("food", 1));
        board.add(new Square("tube"));
        board.add(new Square("zoom", 1));
        board.add(new Square("tube"));
        board.add(new Square("tube"));

        // row 2
        board.add(new Square("tube"));
        board.add(new Square("tube"));
        board.add(new Square("food", 5));
        board.add(new Square("tube"));
        board.add(new Square("tube"));

        // row 3
        board.add(new Square("food", 2));
        board.add(new Square("tube"));
        board.add(new Square("person"));
        board.add(new Square("bars"));
        board.add(new Square("bars"));

        // row 4
        board.add(new Square("zoom", 1));
        board.add(new Square("tube"));
        board.add(new Square("bars"));
        board.add(new Square("tube"));
        board.add(new Square("home"));
    }

    /* Description: gets the array list of squares that makes up the board
     * Returns: the array list of squares
     */
    public ArrayList<Square> getBoard() {
        return this.board;
    }
}
