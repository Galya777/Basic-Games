package TheClient.Logic;

import TheClient.IUserInterfaceContract;
import Server.GameParts;
import Server.GameState;
import TheClient.MakeLogic.SudokuLogic;
import Server.Messages;
import Server.Storage;
import javafx.application.Platform;



import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class ControllLogic implements IUserInterfaceContract.EventListen {


    private Storage storage;
    private final IUserInterfaceContract.View view;

    public ControllLogic(Storage storage, IUserInterfaceContract.View view) {
        this.storage = storage;
        this.view = view;

    }


    @Override
    public void onSudoku(int x, int y, int input) {
        try{
            GameParts gameData=storage.getData();
            int[][] newGridState= gameData.getGrid();
            newGridState[x][y]=input;

            gameData= new GameParts(SudokuLogic.checkForComplete(newGridState),newGridState);

            storage.update(gameData);

            view.updateSquare(x, y, input);

            if(gameData.getState()== GameState.COMPLETE){
                view.ShowDialog(Messages.COMPLETE);
            }
        } catch (IOException e){
            e.printStackTrace();
            view.ShowError(Messages.ERROR);
        }
    }

    @Override
    public void onDialogClick() {
        try{
            storage.update(SudokuLogic.getNewGame());
            view.updateBoard(storage.getData());
        } catch(IOException e){
             view.ShowError(Messages.ERROR);
        }
    }
    /*public void initializeRMI() {
        String host = "localhost";
        try {
            Registry registry = LocateRegistry.getRegistry(host, 1099);
            storage = (Storage) registry.lookup("sudoku");
        } catch (Exception e) {
            System.out.println("ERROR " + e);
            quitApp();
        }
    }*/

    public static void quitApp() {
        Platform.exit();
        System.exit(0);
    }

}
