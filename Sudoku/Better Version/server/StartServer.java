package server;

import helpers.GameParts;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StartServer extends UnicastRemoteObject implements IServer, Remote {

    public StartServer() throws RemoteException {
        super();
    }
    @Override
    public GameParts startTheGame(int difficulty) throws RemoteException {
        return new GameParts(difficulty);
    }
    @Override
    public boolean isSolution(GameParts board) {
        return board.isItSolved();
    }

}
