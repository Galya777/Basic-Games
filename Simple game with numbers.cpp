#include <iostream>
using namespace std;
/*В тази игра двама играчи трябва да достигнат до желано число, като имат право да добавят единица или да умножат вече дадено им число. Губи този, който надмине съответното число.*/
void switchplayer(char player) {
    if (player == 'A') {
        player = 'B';
    }  else {
        player = 'A';
    }
    cout << "It is ypur turn, player " << player << endl;
}
int main()
{
    char player = 'A';
    int N;
    cout << "Enter the number to catch: ";
    cin >> N;
    int k = 2; 
    cout << "It is ypur turn, player " << player << endl;
    while (k < N) {
        cout << "Choose a way: ";
        char way;
        cin >> way;
        if (way == '+') {
            k += 1;
        }
        else if (way == '*') {
            k *= 2;
        }
        switchplayer(player);
    }
    if (k == N) {
        cout << "Player " << player << " you are winner. " << endl;
    } else {
        cout << "Player " << player << " you loose. " << endl;
    }
    return 0;
}

