package Server;

import Server.Build.Build;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


public class Sudoku extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException{
        try {
            Build.build();
        } catch (IOException e){
            e.printStackTrace();
            throw e;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
