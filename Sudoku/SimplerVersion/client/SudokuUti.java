package TheClient.MakeLogic;

import Server.GameParts;

public class SudokuUti {
public static void copyValues(int[][] oldArr, int[][] newArr){
    for(int xIndex=0; xIndex< GameParts.SIZE;++xIndex){
        System.arraycopy(oldArr[xIndex], 0, newArr[xIndex], 0, GameParts.SIZE);
    }
}
public static int[][] copyToNewArray(int[][] oldArr){
    int[][] newArr= new int[GameParts.SIZE][GameParts.SIZE];
    copyValues(oldArr,newArr);

    return newArr;
}
}
