package helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameParts {



    private List<List<Integer>> UserBoard;

    public List<List<Integer>> getSecretBoard() {
        return secretBoard;
    }

    private List<List<Integer>> secretBoard;

    public GameParts(int difficulty) {
        ClearBoard();
        makeBoard();
        GetSolved();
        emptyCells(difficulty);

    }

    public void setNumber(int row, int column, int value) {
        UserBoard.get(row).set(column, value);
    }


    public boolean isItSolved(){

        if(isInvalid()) return false;
        if(notFilled()) return false;
        return true;
    }
    private boolean isInvalid() {
        if(invalidRows()) return true;
        if(invalidCols()) return true;
        return false;
    }

    private boolean invalidRows() {
        for (List<Integer> row : UserBoard) {
            if(repeats(row)) return true;
            if (row.stream().distinct().count() != 9) {
                return true;
            }
        }
        return false;
    }


    private boolean invalidCols() {

        for (int i = 0; i < 9; i++) {
            List<Integer> column = new ArrayList<>();
            if(repeats(column)) return true;
            for (List<Integer> row : UserBoard) {
                    column.add(row.get(i));
            }
            if (column.stream().distinct().count() != 9) {
                return true;
            }
        }
        return false;
    }


    private boolean repeats(List<Integer> row) {
        for(int i=1; i<=9;++i){
            if(Collections.frequency(row, i)>1) return true;
        }
        return false;
    }

    private boolean notFilled() {
        if (UserBoard.stream().anyMatch(integers -> integers.contains(0))) {
            return true;
        }
        return false;
    }

    public void makeBoard(){
        int start=1, now;
        for(List<Integer> row: UserBoard){
            now=start;
            for (int j = 0; j < 9; j++) {
                if (now > 9) {
                    now = 1;
                }

                row.set(j, now++);
            }

            if (UserBoard.indexOf(row) == 0) {
                start = 4;
                continue;
            }

            start = now + 3;

            if (start > 9) {
                start = (start % 9) + 1;
            }
        }
    }
    private void emptyCells(int difficulty) {
        Random rand=new Random();
        while (difficulty > 0) {

            for (List<Integer> row : UserBoard) {

                int index = rand.nextInt(9);
                row.set(index, 0);

                difficulty--;

                if (difficulty == 0) {
                    break;
                }
            }
        }
    }
    public void ClearBoard(){
        this.UserBoard = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                row.add(0);
            }
            UserBoard.add(row);
        }
    }
    private void GetSolved() {
        secretBoard = new ArrayList<>();
        for (List<Integer> row : UserBoard) {
            List<Integer> current = new ArrayList<>(row);
            secretBoard.add(current);
        }
    }

    public List<List<Integer>> getUserBoard() {
        return UserBoard;
    }

    public void print(List<List<Integer>> board){
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 9; i++) {
                String d = i == 2 || i == 5 ? " | " : " ";
                System.out.print(board.get(j).get(i) + d);
            }
            String d = j == 2 || j == 5 ? "\n---------------------\n" : "\n";
            System.out.print(d);
        }
    }


    public String getCell(int row, int col) {
        if(row < 0 || row > 8 || col < 0 || col > 8)
            return null;

        return UserBoard.get(row).get(col).toString();
    }
}
