#include <iostream>
#include <SFML/Graphics.hpp>
#include <time.h>
using namespace std;
using namespace sf;

const int MAX = 100;
void process(char board[][MAX], int Lines) {
    for (int i = 1; i <= 10; i++)
        for (int j = 1; j <= 10; j++)
        {
            int n = 0;
            if (board[i][j] == 9) continue;
            if (board[i + 1][j] == 9) n++;
            if (board[i][j + 1] == 9) n++;
            if (board[i - 1][j] == 9) n++;
            if (board[i][j - 1] == 9) n++;
            if (board[i + 1][j + 1] == 9) n++;
            if (board[i - 1][j - 1] == 9) n++;
            if (board[i - 1][j + 1] == 9) n++;
            if (board[i + 1][j - 1] == 9) n++;
            board[i][j] = n;
        }
}
int main()
{
    srand(time(0));
    RenderWindow app(VideoMode(400, 400), "Mine Sweeper!");

    char board[MAX][MAX], userboard[MAX][MAX];
    int Lines, Colons, bombs;
    int w = 32;
    Texture t2;
    t2.loadFromFile("image/tillllles.jpg");
    Sprite things(t2);
    cout << "Enter the size of your board!";
    cin >> Lines;
    Colons = Lines;
    cout << "Enter the number of bombs: ";
    cin >> bombs;
    for (int i = 1; i <= Lines; ++i) {
        for (int j = 1; j <= Colons; ++j) {
            userboard[i][j] = 10;
            if (rand() % bombs == 0)  board[i][j] = 9;
            else board[i][j] = 0;
        }
    } process(board, Lines);
    while (app.isOpen()) {
        Vector2i pos = Mouse::getPosition(app);
        int x = pos.x / w;
        int y = pos.y / w;
        Event e;
        while (app.pollEvent(e)) {
            if (e.type == Event::Closed) {
                app.close();
            }
            if (e.type == Event::MouseButtonPressed) {
                if (e.key.code == Mouse::Left) {
                    userboard[x][y] = board[x][y];
                }
                else if (e.key.code == Mouse::Right) {
                    userboard[x][y] = 11;
                }
            }
        }
        app.clear(Color::Magenta);
        for (int i = 1; i <= 10; ++i) {
            for (int j = 1; j <= 10; ++j) {
                if (userboard[x][y] == 9) userboard[i][j] = board[i][j];
                things.setTextureRect(IntRect(userboard[i][j] * w, 0, w, w));
                things.setPosition(i * w, j * w);
                app.draw(things);
            }
        }
        app.display();
    }
        return 0;
    }


