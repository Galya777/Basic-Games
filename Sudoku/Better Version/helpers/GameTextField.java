package helpers;

import javafx.scene.control.TextField;

public class GameTextField extends TextField {
    private final int x;
    private final int y;

    public GameTextField(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public GameTextField(String text, int x, int y) {
        super(text);
        this.x = x;
        this.y = y;
    }

    @Override
    public void replaceText(int i, int i1, String s){
        if(!s.matches("[0-9]")){
            super.replaceText(i, i1, s);
        }
    }

    @Override
    public void replaceSelection(String s){
        if(!s.matches("[0-9]")){
            super.replaceSelection(s);
        }
    }

}
