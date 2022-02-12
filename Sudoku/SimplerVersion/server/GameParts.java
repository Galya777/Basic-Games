package Server;

import Server.addIns.Level;
import TheClient.MakeLogic.SudokuUti;

import java.io.Serializable;

public class GameParts implements Serializable {
    private final GameState state;
    private final int[][] grid;

    public static final int SIZE=9;

    public GameParts(GameState state, int[][] grid) {
        this.state = state;
        this.grid = grid;
    }




    public GameState getState() {
        return state;
    }

    public int[][] getGrid() {
        return SudokuUti.copyToNewArray(grid);
    }
}
