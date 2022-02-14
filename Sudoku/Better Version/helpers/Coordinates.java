package helpers;

import java.util.Objects;

public class Coordinates {
    private final int x;
    private final int y;
    private int value;



    private int previousValue;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
        this.value = 0;
    }
    public Coordinates(Coordinates otherCell) {
        this.x = otherCell.x > 0 && otherCell.x < 9 ? otherCell.x : 0;
        this.y = otherCell.y > 0 && otherCell.y < 9 ? otherCell.y : 0;
        this.value = otherCell.value >= 1 && otherCell.value <= 9 ? otherCell.value : 0;
        this.previousValue = otherCell.previousValue >= 1 && otherCell.previousValue <= 9 ? otherCell.previousValue : 0;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPreviousValue() {
        return previousValue;
    }
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null|| getClass() !=o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getValue() {
        return value;
    }
}
