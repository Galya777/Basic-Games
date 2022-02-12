package Server;

import Server.addIns.Level;
import Server.addIns.UserInformation;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Storage extends Remote {
    void update(GameParts game) throws IOException;
    GameParts getData() throws IOException;
    public GameParts generateSudoku(Level level) throws RemoteException;
    GameParts showSolution() throws RemoteException;
    void recordStatistics(UserInformation user) throws RemoteException;
}
