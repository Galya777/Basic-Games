package TheClient.MakeLogic;

import Server.Coordinates;
import Server.GameParts;

import Server.addIns.Level;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {
    private static final Level currentLevel=Level.EASY;


    public static int[][] getNeGrid(){
        return unsolved(getSolved());
    }

    private static int[][] unsolved(int[][] solved) {
        Random r=new Random(System.currentTimeMillis());
        boolean solvable= false;
        int[][] solveableArr= new int[GameParts.SIZE][GameParts.SIZE];

        while(!solvable){
            SudokuUti.copyValues(solved, solveableArr);

            int index=0;

            while(index< currentLevel.getBlanks()){
                int xCoord= r.nextInt(GameParts.SIZE);
                int yCoord= r.nextInt(GameParts.SIZE);

                if(solveableArr[xCoord][yCoord]!=0){
                    solveableArr[xCoord][yCoord]=0;
                    index++;
                }
            }

            int[][] toBeSolved= new int[GameParts.SIZE][GameParts.SIZE];
            SudokuUti.copyValues(solveableArr, toBeSolved);

            solvable= Solver.puzzleSolvable(toBeSolved);


        }


        return solveableArr;
    }

    public static int[][] getSolved() {
        Random r=new Random(System.currentTimeMillis());
        int [][] newGrid= new int[GameParts.SIZE][GameParts.SIZE];

        for(int value=1; value<=GameParts.SIZE;++value){
            int allocate=0;
            int interrupt=0;

            List<Coordinates> tracker= new ArrayList<>();
            int attempts=0;

            while(allocate<GameParts.SIZE){
                if(interrupt>200){
                    for (Coordinates coord : tracker) {
                        newGrid[coord.getX()][coord.getY()] = 0;
                    }
                    interrupt=0;
                    allocate=0;
                    tracker.clear();
                    attempts++;

                    if(attempts>500){
                        clearArr(newGrid);
                        attempts=0;
                        value=1;
                    }
                }
                int xCoord= r.nextInt(GameParts.SIZE);
                int yCoord= r.nextInt(GameParts.SIZE);

                if(newGrid[xCoord][yCoord]==0){
                    newGrid[xCoord][yCoord]=value;

                    if(SudokuLogic.isInvalid(newGrid)){
                        newGrid[xCoord][yCoord]=0;
                        interrupt++;
                    }else{
                        tracker.add(new Coordinates(xCoord, yCoord));
                        allocate++;
                    }
                }
            }
        }

       return newGrid;
    }

    private static void clearArr(int[][] newGrid) {
         for(int x=0; x<GameParts.SIZE;++x){
             for(int y=0; y<GameParts.SIZE;++y){
              newGrid[x][y]=0;
             }
         }
    }


}
