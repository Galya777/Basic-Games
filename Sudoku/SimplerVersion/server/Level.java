package Server.addIns;

import java.util.Random;

public enum Level {
    EASY(30, 45),
    MEDIUM(46, 50),
    HARD(51, 60);

    private final int minBlankCells;
    private final int maxBlankCells;

    Level(int minBlankCells, int maxBlankCells) {
        this.minBlankCells = minBlankCells;
        this.maxBlankCells = maxBlankCells;
    }

    public int getBlanks(){
        return new Random().nextInt((maxBlankCells-minBlankCells)+1+minBlankCells);
    }

    @Override
    public String toString() {
        return switch (this) {
            case EASY -> "easy";
            case MEDIUM -> "medium";
            case HARD -> "hard";
        };
    }
}
