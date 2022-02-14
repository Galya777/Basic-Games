package server;

import helpers.GameParts;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {

    GameParts startTheGame(int difficulty) throws RemoteException;
    public boolean isSolution(GameParts board);
}
