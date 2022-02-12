package TheClient.MakeLogic;

import Server.GameParts;
import Server.GameState;
import Server.Rows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SudokuLogic {

    public static GameParts getNewGame(){
        return new GameParts(GameState.NEW, Generator.getNeGrid());
    }

    public static GameState checkForComplete(int[][] grid){
        if(isInvalid(grid)) return GameState.ACTIVE;
        if(notFilled(grid)) return GameState.ACTIVE;
        return GameState.COMPLETE;
    }

    protected static boolean isInvalid(int[][] grid) {
        if(invalidRows(grid)) return true;
        if(invalidCols(grid)) return true;
        return invalidSquare(grid);
    }

    private static boolean invalidRows(int[][] grid) {
        for(int y=0; y<GameParts.SIZE;++y) {
            List<Integer> row = new ArrayList<>();
            for (int x = 0; x < GameParts.SIZE; ++x) {
                row.add(grid[x][y]);
            }
            if (collectionRepeats(row)) return true;
        }
        return false;
    }

    private static boolean invalidCols(int[][] grid) {
        for(int x=0; x<GameParts.SIZE;++x) {
            List<Integer> row = new ArrayList<>();
            for (int y = 0; y < GameParts.SIZE; ++y) {
                row.add(grid[x][y]);
            }
            if (collectionRepeats(row)) return true;
        }
        return false;
    }

    private static boolean invalidSquare(int[][] grid) {
        if(rowOfSquareInvalid(Rows.TOP, grid)) return true;
        if(rowOfSquareInvalid(Rows.MIDDLE, grid)) return true;
        return rowOfSquareInvalid(Rows.BOTTOM, grid);
    }

    private static boolean rowOfSquareInvalid(Rows value, int[][] grid) {
        switch (value){
            case TOP:
                if(SquareInvalid(0,0, grid)) return true;
                if(SquareInvalid(0,3, grid)) return true;
                return SquareInvalid(0, 6, grid);
            case MIDDLE:
                if(SquareInvalid(3,0, grid)) return true;
                if(SquareInvalid(3,3, grid)) return true;
                return SquareInvalid(3, 6, grid);
            case BOTTOM:
                if(SquareInvalid(6,0, grid)) return true;
                if(SquareInvalid(6,3, grid)) return true;
                return SquareInvalid(6, 6, grid);
            default: return false;
        }
    }

    private static boolean SquareInvalid(int x, int y, int[][] grid) {
        int yEnd=y+3;
        int xEnd=x+3;

        List<Integer> square=new ArrayList<>();

        while(y<yEnd){
            while(x<xEnd){
                square.add(grid[x][y]);
                ++x;
            }
            x-=3;
            ++y;
        }
        return collectionRepeats(square);
    }

    private static boolean collectionRepeats(List<Integer> collection) {
        for(int i=1; i<=GameParts.SIZE;++i){
            if(Collections.frequency(collection, i)>1) return true;
        }
      return false;
    }

    private static boolean notFilled(int[][] grid) {
        for(int x=0; x<GameParts.SIZE;++x){
            for(int y=0; y<GameParts.SIZE;++y){
                if(grid[x][y]==0) return true;

            }
        }
        return false;
    }
}
