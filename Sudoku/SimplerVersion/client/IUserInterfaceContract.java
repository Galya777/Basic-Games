package TheClient;

import Server.GameParts;

public interface IUserInterfaceContract {
interface EventListen{
    void onSudoku(int x, int y, int input);
    void onDialogClick();
    //void initializeRMI();
}

interface View{
    void setListener(IUserInterfaceContract.EventListen listener);
    void updateSquare(int x, int y, int input);
    void updateBoard(GameParts gameParts);
    void ShowDialog(String message);
    void ShowError(String message);


  }

}
