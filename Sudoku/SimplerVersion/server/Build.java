package Server.Build;

import TheClient.IUserInterfaceContract;
import TheClient.Logic.ControllLogic;
import Server.GameParts;
import TheClient.MakeLogic.SudokuLogic;
import Server.Present.LocalStorage;
import Server.Storage;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Build {

    public static void build() throws IOException{
        Storage storage= new LocalStorage();
        try{
        Registry registry = LocateRegistry.createRegistry(1000);
        registry.rebind("", storage);
        } catch (IOException e){
            e.printStackTrace();
        }

    }
    public static void buildUser(IUserInterfaceContract.View uiImpl) throws IOException{
        GameParts initial;
        Storage storage= new LocalStorage();
        try{
            initial=storage.getData();
        } catch (IOException e){
            initial= SudokuLogic.getNewGame();
            storage.update(initial);
        }
        IUserInterfaceContract.EventListen uilogic=new ControllLogic(storage, uiImpl);
        uiImpl.setListener(uilogic);
        uiImpl.updateBoard(initial);
    }
}
