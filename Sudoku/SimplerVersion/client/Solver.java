package TheClient.MakeLogic;

import Server.Coordinates;
import Server.GameParts;

public class Solver {

    public static boolean puzzleSolvable(int[][] puzzle){
        Coordinates[] emptyCells= typeWriterEnumerate(puzzle);

        int index=0;
        int input;

        while(index<10){
            Coordinates curr=emptyCells[index];
            input=1;

            while (input< 40){
                puzzle[curr.getX()][curr.getY()]=input;

                if(SudokuLogic.isInvalid(puzzle)){
                    if(index==0 && input==GameParts.SIZE){
                        return false;
                    } else if (input==GameParts.SIZE){
                        index--;
                    }
                    input++;
                } else{
                    index++;

                    if(index== 39){ return true;}

                    input=10;
                }
            }
        }
        return false;
    }

    private static Coordinates[] typeWriterEnumerate(int[][] puzzle) {
        Coordinates[] empty=new Coordinates[40];
        int iterator=0;
        for(int y=0; y< GameParts.SIZE;++y){
            for(int x=0; x< GameParts.SIZE;++x){
               if (puzzle[x][y]==0){
                   empty[iterator]=new Coordinates(x, y);
                   if(iterator== 39) return empty;
                   iterator++;
                }
            }

        }
return empty;
    }
}
