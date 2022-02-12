package Server.Present;

import Server.GameState;
import Server.addIns.Level;
import Server.addIns.UserInformation;
import Server.GameParts;
import Server.Storage;
import TheClient.MakeLogic.Generator;

import java.io.*;
import java.rmi.RemoteException;

public class LocalStorage implements Storage {
    private static final File Data=new File(System.getProperty("user.home"), "data.txt");


    @Override
    public void update(GameParts game) throws IOException {
        try{
            FileOutputStream fileOutputStream=new FileOutputStream(Data);
            ObjectOutputStream objectOutputStream= new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(game);
            objectOutputStream.close();
        } catch(IOException e){
          throw new IOException("Unable to access The Data");
        }
    }

    @Override
    public GameParts getData() throws IOException {
        FileInputStream fileInputStream=new FileInputStream(Data);
        ObjectInputStream objectInputStream= new ObjectInputStream(fileInputStream);
        try{
            GameParts gameParts =(GameParts) objectInputStream.readObject();
            objectInputStream.close();
            return gameParts;
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            throw new IOException("File not found");
        }

    }

    @Override
    public GameParts generateSudoku(Level level) throws RemoteException {
        return new GameParts(GameState.NEW, Generator.getNeGrid());
    }

    @Override
    public GameParts showSolution() throws RemoteException {
        return null;
    }

    @Override
    public void recordStatistics(UserInformation user) throws RemoteException {
        try {
            File statsFile = new File("statistics.txt");
            if(!statsFile.exists())
                statsFile.createNewFile();

            BufferedWriter writer = new BufferedWriter(new FileWriter(statsFile, true));
            writer.write(String.format("%s%n", user));
            System.out.println("Successfully written in file.");

            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in trying to record statistics.");
        }
    }
}
