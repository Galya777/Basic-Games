#include <iostream>
#include <time.h>
using namespace std;
const int MAX_HEI = 21;
const int MAX_WID = 41;
char board[MAX_HEI][MAX_WID] = { ' ' };
char gamer = 'X';

void printboard(char board[][MAX_WID],int heigh, int width) {
    system("cls");
    for (int i = 0; i < heigh; i++) {
        for (int j = 0; j < width; j++) {
            cout << "| " << board[i][j];
        }
        cout << " | ";
        cout << endl;
    }
}
char switchplayer(char gamer) {
    if (gamer == 'X') {
        gamer = 'O';
    }
    else {
        gamer = 'X';
    }
    return gamer;
}
void Add(int wid,int hei, int square) {
        int row = hei-1;
        if (square >= hei) {
            cout << "Wrong input! Enter valid colon!";
            int newsquare;
            cin >> newsquare;
            Add(wid, hei, newsquare);
        }    else {
            while (board[row][square - 1] == 'X' || board[row][square - 1] == 'O') {
                row--;
            }  if (row < 0) {
                cout << "Colon is full! Please, choose another!";
                int newsquare;
                cin >> newsquare;
                Add(wid, hei, newsquare);
            }    else {
                board[row][square - 1] = gamer;
            }
        }
}
bool winner(char board[][MAX_WID], int wid, int hei){
    
    for (int i = 0; i < wid; i++)
    {
        for (int j = 0; j < hei; j++)
        {
            if (board[j][i] == 'X' && board[j + 1][i + 1] == 'X' && board[j + 2][i + 2] == 'X' && board[j + 3][i + 3] == 'X')
            {
                return true;
                break;
            }
            else if (board[j][i] == 'X' && board[j + 1][i - 1] == 'X' && board[j + 2][i - 2] == 'X' && board[j + 3][i - 3] == 'X')
            {
                return true;
                break;
            }
            else if (board[j][i] == 'O' && board[j + 1][i - 1] == 'O' && board[j + 2][i - 2] == 'O' && board[j + 3][i - 3] == 'O')
            {
                return true;
                break;
            }
            else if (board[j][i] == 'O' && board[j - 1][i - 1] == 'O' && board[j - 2][i - 2] == 'O' && board[j - 3][i - 3] == 'O')
            {
                return true;
                break;
            }
            else if (board[j][i] == 'X' && board[j][i - 1] == 'X' && board[j][i - 2] == 'X' && board[j][i - 3] == 'X')
            {
                return true;
                break;
            }
            else if (board[j][i] == 'X' && board[j-1][i] == 'X' && board[j-2][i] == 'X' && board[j-3][i] == 'X')
            {
                return true;
                break;
            }

        }
    }
    return false;
}
int main()
{
    int width, heigh;
    cin >> heigh >> width;
    if (width<4 && width>MAX_WID - 1&&heigh<4&&heigh>MAX_HEI-1) {
        cout << "Wrong input!";
        cin >> heigh >> width;
    }
    int counter = 1;
    printboard(board, heigh, width);
    int way;
    cout << "Choose your opponent:" << endl;
    cout << "1.Another player" << endl;
    cout << "2.Computer" << endl;
        cin >> way;
    if (way != 1 || way != 2) {
            cout << "We have not such an opponent! Please, enter a valid option! " << endl;
            cin >> way;
    } while (winner(board,width,heigh) == false) {
        int number1;
        if (way==2 && gamer=='O') {
            srand(time(NULL));
            number1 = rand() % 4;
        } else {
            cin >> number1;
        }
        Add(width, heigh, number1);
        counter++;
        gamer = switchplayer(gamer);
        printboard(board, heigh, width);
        if (counter == heigh * width) {
            cout << "It's a draw!" << endl;
            break;
        }
        if (gamer == 'X') {
            cout << "Player 1, It is your turn!:" << endl;
        }
        else {
            cout << "Player 2, It is your turn!:" << endl;
        }
        if (winner(board, width, heigh) == true) {
            if (gamer == 'X') {
                cout << "Player2, you win!";
            }
            else {
                cout << "Player1, you win!";
            }
            break;
        }
    }
    
    return 0;
}

