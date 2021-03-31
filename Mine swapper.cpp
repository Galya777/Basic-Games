#include <iostream>
#include <time.h>
constexpr auto CELFREE = '$';;
constexpr auto BOMB = '#';;
constexpr auto UP =  'w';;
constexpr auto DOWN = 'x';
constexpr auto LEFT =  'l';;
constexpr auto RIGHT = 'r';;
constexpr auto POSITION = '@';;

using namespace std;

bool finished = false;
const int MAX = 1000;
 char board[MAX][MAX], userboard[MAX][MAX];
 int userline = 0;
 int usercol = 0;
 bool isinside(int row, int col, int Lines, int Colons) {
     return 0 <= row && row < Lines && 0 <= col&& col < Colons;
 }
     
void putbombs(int bombs, int Lines, int Colons) {
    srand(time(0));
    int p = 0;
    while (p < bombs) {
        int row = rand() % Lines;
        int col = rand() % Colons;
        if (board[row][col] == CELFREE) {
            board[row][col] = BOMB;
            p++;
        }
    }
} bool finish(int bombs, int Lines, int Colons) {
    int used = Lines*Colons;
    for (int i = 0; i < Lines; ++i) {
        for (int j = 0; j < Colons; ++j) {
            if (userboard[i][j] != CELFREE && userboard[i][j] != POSITION) {
                used--;
            }
        }
    }if (used == 0) {
            cout << "Congradulations!" << endl;
            cout << "You won! " << endl;
            return true;
        }
    return false;
}
int countbombs(int row, int col, int Lines, int Colons) {
    int num = 0;
    for (int offr = -1; offr <= 1; ++offr) {
        for (int offc = -1; offc <= 1; ++offc) {
            int nextline = row + offr, nextcol = col + offc;
            if (isinside(nextline, nextcol,Lines, Colons) && board[nextline][nextcol] == BOMB) {
                ++num;
            }
        }


    }
    return num;
}
void draw(int bombs, int Lines, int Colons) {
    for (int i = 0; i < Lines; ++i) {
        for (int j = 0; j < Colons; ++j) {
            board[i][j] = CELFREE;
            userboard[i][j] = CELFREE;
        }
    }
    putbombs(bombs, Lines, Colons);
    for (int i = 0; i < Lines; ++i) {
        for (int j = 0; j < Colons; ++j) {
            if (board[i][j] != BOMB) {
                board[i][j] = countbombs(i, j,Lines,Colons) + '0';
            }
        }
    }
}
void print(int Lines, int Colons) {
    for (int i = 0; i < Lines; ++i) {
        for (int j = 0; j < Colons; ++j) {
            cout << userboard[i][j];
        }
        cout << endl;
    }
}
void trigger(int row, int col, int Lines, int Colons) {
    if (userboard[row][col] != CELFREE && userboard[row][col] != POSITION) {
        return;
    }
    if (board[row][col] == BOMB) {
        finished = true;
        cout << "Game over!" <<endl;
        userboard[row][col] = BOMB;
        for (int i = 0; i < Lines; ++i) {
            for (int j = 0; j < Colons; ++j) {
                if (board[i][j] == BOMB) {
                    cout << BOMB;
                }
                else {
                    cout << userboard[i][j];
                }
            }
            cout << endl;
        } 
    }  else {
        userboard[row][col] = board[row][col];
        if (userboard[row][col] == '0') {
            for (int offr = -1; offr <= 1; ++offr) {
                for (int offc = -1; offc <= 1; ++offc) {
                    int nextline = row + offr, nextcol = col + offc;
                    if (isinside(nextline, nextcol,Lines, Colons)) {
                        trigger(nextline, nextcol,Lines, Colons);
                    }
                }
            }
        }
    }
}
void process(char command, int Lines, int Colons) {
    int next_l = userline, next_co = usercol;
    if (command == UP) {
        --next_l;
    } else if (command == DOWN) {
        ++next_l;
    }
    else if (command == LEFT) {
        --next_co;
    }
    else if (command == RIGHT) {
        ++next_co;
    }
    if (!isinside(next_l, next_co, Lines, Colons)) {
        return;
    }
    userboard[userline][usercol] = board[userline][usercol];
        userline = next_l;
        usercol = next_co;
        userboard[userline][usercol] = POSITION;

            trigger(userline, usercol, Lines, Colons);
        
    
}
void start(int bombs, int Lines, int Colons) {
    userline = usercol = 0;
    finished = false;
    draw(bombs, Lines, Colons);
    userboard[userline][usercol] = POSITION;
}
int main()
{
    int Lines, Colons,bombs;
    cout << "Enter the size of your board!";
    cin >> Lines;
    Colons = Lines;
    cout << "Enter the number of bombs: ";
    cin >> bombs;
    start(bombs,Lines,Colons);
    while (!finished) {
        if (finish(bombs, Lines, Colons) == true && finished == false) {
            break;
        }
        print(Lines, Colons);
        char command;
        cout << "Enter a command: " << endl;
        cin >> command;
        process(command, Lines, Colons);
        
    }
    return 0;
}

