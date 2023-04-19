package edu.sdsmt.hamsterrun_johnson_noah;

import java.sql.Array;
import java.util.ArrayList;

import edu.sdsmt.hamsterrun_johnson_noah.Square;

public class Board {
    public ArrayList<Square> board = new ArrayList<>();

    Board() {
        // row 0
        board.add(new Square(new Location(0, 0), "tube"));
        board.add(new Square(new Location(1, 0), "tube"));
        board.add(new Square(new Location(2, 0), "bars"));
        board.add(new Square(new Location(2, 0), "food", 10));
        board.add(new Square(new Location(2, 0), "tube"));

        // row 1
        board.add(new Square(new Location(0, 1), "food", 1));
        board.add(new Square(new Location(1, 1), "tube"));
        board.add(new Square(new Location(2, 1), "zoom", 1));
        board.add(new Square(new Location(3, 1), "tube"));
        board.add(new Square(new Location(4, 1), "tube"));

        // row 2
        board.add(new Square(new Location(0, 2), "tube"));
        board.add(new Square(new Location(1, 2), "tube"));
        board.add(new Square(new Location(2, 2), "food", 5));
        board.add(new Square(new Location(3, 2), "tube"));
        board.add(new Square(new Location(4, 2), "tube"));

        // row 3
        board.add(new Square(new Location(0, 3), "food", 2));
        board.add(new Square(new Location(1, 3), "tube"));
        board.add(new Square(new Location(2, 3), "person"));
        board.add(new Square(new Location(3, 3), "bars"));
        board.add(new Square(new Location(4, 3), "bars"));

        // row 4
        board.add(new Square(new Location(0, 4), "zoom", 1));
        board.add(new Square(new Location(1, 4), "tube"));
        board.add(new Square(new Location(2, 4), "bars"));
        board.add(new Square(new Location(3, 4), "tube"));
        board.add(new Square(new Location(4, 4), "home"));
    }
}
