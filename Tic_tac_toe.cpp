#include <iostream>
#include <time.h>
using namespace std;
const int MAX = 9;
char board[MAX][MAX];
char gamer = 'X';

void drawBoard(char arr[][MAX], int rows, int cols)
{
    for (int row = 0; row < rows; row++)
    {
        for (int col = 0; col < cols; col++)
        {
             arr[row][col]='7';
        }
    }
}

void printBoard(const char arr[][MAX], int rows, int cols)
{
    system("cls");
    cout << "Tic Tac Toe" << endl;
    for (int row = 0; row < rows; row++)
    {
        for (int col = 0; col < cols; col++)
        {
            std::cout << arr[row][col] << '\t';
        }
        std::cout << '\n';
    }
}
void SwitchMe(int way) {
    if (way == 1) {
        if (gamer == 'X') {
            gamer = 'O';
            cout << "Player 2, It is your turn!:" << endl;
        }
        else {
            gamer = 'X';
            cout << "Player 1, It is your turn!:" << endl;
        }
    }
    else if (way == 2) {
        if (gamer == 'X') {
            gamer = 'O';
        }
        else {
            gamer = 'X';
            cout << "Player 1, It is ypur turn!:" << endl;
        }
    }
    else if (way == 3) {
        if (gamer == 'X') {
            gamer = 'O';
            
        }
        else {
            gamer = 'X';
            cout << "PC1, It is ypur turn!:" << endl;
            
        }
    }

}
void Add(int N, int square,int way) {

    if (square <= N * N) {
        int row, col;
        row = (square - 1) / N;
        col = (square - 1) % N;
        
        if (board[row][col] == 'X' || board[row][col] == 'O') {
            cout << "The field is already chosen. Try again!" << endl;
            int newsquare;
            if (way == 1 || (way == 2 && gamer == 'X')) {
                cin >> newsquare;
            }   else {
                srand(time(NULL));
                newsquare =rand()%N*N;
            }
            Add(N, newsquare,way);
        }   else {
            board[row][col] = gamer;
        }
    }
}
bool validate(char board[][MAX],int length, int x, int y ){

    if (x < 1 || y < 1 || x > length || y > length) {
        return false;
    }

    if (board[x - 1][y - 1] == 'X' || board[x - 1][y - 1] == 'O') {
        return false;
    }

    return true;
}
bool checkRow(char board[][MAX], int N) {
    bool endGame = true;
    for (int i = 0; i < N; i++)
    {
        for (int j = 0; j < N - 1; j++)
        {
            if (board[i][j] != board[i][j + 1] || (board[i][j] != 'X' && board[i][j] != 'O')) {
                endGame = false;
                break;
            }
        }

        if (endGame) {
            return true;
        }

        endGame = true;
    }

    return false;
}
bool checkColl(char board[][MAX], int N) {
    bool endGame = true;
    for (int i = 0; i < N; i++)
    {
        for (int j = 0; j < N - 1; j++)
        {
            if (board[j][i] != board[j + 1][i] || (board[j + 1][i] != 'X' && board[j + 1][i] != 'O')) {
                endGame = false;
                break;
            }
        }

        if (endGame) {
            return true;
        }

        endGame = true;
    }

    return false;
}
bool checkDiag(char board[][MAX], int N) {
    bool endGame = true;

    for (int i = 0; i < N - 1; i++)
    {
        if (board[i][i] != board[i + 1][i + 1] || (board[i][i] != 'X' && board[i][i] != 'O')) {
            endGame = false;
            break;
        }
    }

    if (endGame) {
        return true;
    }

    endGame = true;
    for (int i = 0; i < N - 1; i++)
    {
        if (board[(N - 1) - i][i] != board[(N - 2) - i][i + 1] || (board[(N - 1) - i][i] != 'X' && board[(N - 1) - i][i] != 'O')) {
            endGame = false;
            break;
        }
    }

    if (endGame) {
        return true;
    }

    return false;
}
bool checkForEnd(char arr[][MAX], const int length) {
    bool endGame;
    endGame = checkRow(arr, length);
    if (endGame) {
        return true;
    }
    endGame = checkColl(arr, length);
    if (endGame) {
        return true;
    }
    endGame = checkDiag(arr, length);
    if (endGame) {
        return true;
    }

    return false;
}
void printResult(int counter, int input) {
	if (counter % 2 == 0) {
		cout << "Player 1 won!" << endl;
	}
	else {
		if (input == 2) {
			cout << "Computer won!" << endl;
		}
		else {
			cout << "Player 2 won!" << endl;
		}
	}
}
           
int main()
{
    srand(time(NULL));
    int N;
    cout << "Tic Tac Toe" << endl;
    cin >> N;
    drawBoard(board, N, N);
    printBoard(board, N, N);
    cout << "Choose kind of game!: " << endl;
    cout << "1.Player vs. player" << endl;
    cout << "2.Player vs. computer" << endl;
    cout << "3.Computer vs. computer" << endl;
    int way;
    int counter = 1;
    int square;
    cin >> way;
    switch (way) {
    case 1:
        while (checkForEnd(board, N) == false) {
            cout << "Choose your field!" << endl;
            cout << gamer << " it is your turn!" << endl;
            cin >> square;
            Add(N, square,1);
            counter++;
		printBoard(board, N, N);
            SwitchMe(1);
            
            if (counter == N*N) {
                cout << "It is a draw.";
                break;
            }
        } if (checkForEnd(board, N) == true) {
            printResult(counter, way);
                break;
        } 
    case 2:
        while (checkForEnd(board, N) == false) {
            cout << "Choose your field!" << endl;
            cout <<gamer<< " it is your turn!" << endl;
            if (gamer == 'X') {
                cin >> square;
            }   else {
                square = rand() % N * N;
            }
            Add(N, square,2);
            counter++;
		printBoard(board, N, N);
            SwitchMe(2);
        }if (checkForEnd(board, N) == true) {
            printResult(counter, way);
            break;
        }else if (counter == N*N && checkForEnd(board, N) == false) {
            cout << "It is a draw.";
            break;
        }
    case 3:
        while (checkForEnd(board, N) == false) {
            cout << gamer << " it is your turn!" << endl;
            square = rand() % N * N;
            Add(N, square,3);
            counter++;
		printBoard(board, N, N);
            SwitchMe(3);
        }if (checkForEnd(board, N) == true) {
            printResult(counter, way);
            break;
        }else if (counter == N*N&& checkForEnd(board, N) == false) {
            cout << "It is a draw.";
            break;
        }

    }
    return 0;
}

