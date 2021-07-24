#pragma once
#include "Card.h"
#include "Deck.h"
#include "Player.h"
#include <fstream>
class Game
{
public:
	Game();
	void run();
	void showPlayers() const;
	bool firstPlayerGame();
	void dealerGame();

	bool readPlayers();
	void writePlayers();
private:
	Player* players;
	int playersCount;

	Deck deck;
	Player currentPlayer;
	int dealerPoints;
	bool isPlayerfinished;

	int includePlayer(const char* name);
	void setPlayer(char* name);
	void savePlayers();
	void setDeck(char* token);
	const int convertPoints(char* points) const;
	bool checkName(char* name);
};

