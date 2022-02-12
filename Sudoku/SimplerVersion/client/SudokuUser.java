package TheClient;

import Server.Build.Build;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;

import java.util.Optional;

public class SudokuUser extends Application {
   private static String username;

    public static String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if(username != null) SudokuUser.username = username;
        else SudokuUser.username = "";
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        TextInputDialog inputDialog=new TextInputDialog();
        inputDialog.setContentText("Enter your username: ");
        Optional<String> enteredUsername = inputDialog.showAndWait();

        if(enteredUsername.isPresent()) {
            setUsername(enteredUsername.get());
        }else{
            Platform.exit();
            System.exit(0);
        }

        IUserInterfaceContract.View uiImpl = new UserInterface1(primaryStage);
        try {
            Build.buildUser(uiImpl);

        } catch (IOException e){
            e.printStackTrace();
            throw e;

        }

    }
    public static void main(String[] args) {
        launch(args);
    }
}
