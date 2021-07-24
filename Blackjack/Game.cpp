#include <iostream>
#include <fstream>
#include <cstring>
#include <time.h> 
#include "Game.h"
const int LENGTH = 30;
const char* fileName = "players.db";
Game::Game()
{
	char input[LENGTH];
	if (readPlayers()) {
		showPlayers();
		std::cout << "Choose a player or enter a new player" << std::endl;
	}
	else {
		playersCount = 0;
		std::cout << "No player in database. Please enter a new Player:" << std::endl;
	}

	bool isGoodName = true;
	do {
		if (!isGoodName) {
			std::cout << "Invalid name. Please try again: ";
		}
		std::cin.getline(input, LENGTH);
		isGoodName = false;
	} while (!checkName(input));
	setPlayer(input);

	std::cout << "You will play as " << input << ". Choose the size of deck:";
	std::cin.ignore();
	std::cin.getline(input, LENGTH);
	setDeck(input);
}

void Game::run()
{
	std::cout << "Start!" << std::endl;
	int games = 1;
	if (!firstPlayerGame()) {
		std::cout << "You loose!" << std::endl;
		currentPlayer.getLoose(games);
		++games;
	}
	else {
		dealerPoints = 0;
		dealerGame();
		if (dealerPoints > 21 || dealerPoints <= currentPlayer.getCurrentPoints()) {
			std::cout << "You win!" << std::endl;
			currentPlayer.getWin(games);
			++games;
		}
		else {
			std::cout << "You loose!" << std::endl;
			currentPlayer.getLoose(games);
			++games;
		}
	}

	savePlayers();
}

void Game::showPlayers() const
{
	for (int i = 0; i < playersCount; i++)
	{
		std::cout << players[i];
	}
}

bool Game::firstPlayerGame()
{
	isPlayerfinished = false;
	Card currentCard = deck.draw();
	char value[1];
	value[0]= currentCard.getValue();
	currentPlayer.addCurrentPoints(convertPoints(value));
	std::cout << currentCard << "	(Points: " << currentPlayer.getCurrentPoints() << ")" << std::endl;

	char input[LENGTH];
	for (;;) {
		if (currentPlayer.getCurrentPoints() > 21) {
			return false;
		}
		std::cout << "hit/stand/probability" << std::endl;
		std::cin >> input;

		if (strcmp(input, "hit") == 0) {
			currentCard = deck.draw();
			currentPlayer.addCurrentPoints(convertPoints(value));
			std::cout << currentCard << "	(Points: " << currentPlayer.getCurrentPoints() << ")" << std::endl;
		}
		else if (strcmp(input, "stand") == 0) {
			break;
		}
		else {
			std::cout << "Unknown command! Please try again." << std::endl;
		}
	}

	return true;
}

void Game::dealerGame()
{

	isPlayerfinished = true;
	std::cout << "The dealer's draw: ";
	do {
		Card currentCard = deck.draw();
		char value[1];
		value[0] = currentCard.getValue();
		std::cout << currentCard << " ";
		dealerPoints += convertPoints(value);

	} while (dealerPoints <= 17);

	std::cout << "	(Points: " << dealerPoints << ")" << std::endl;
}

bool Game::readPlayers()
{
	std::ifstream in(fileName, std::ios::binary);
	if (!in) {
		return false;
	}

	in.read((char*)&playersCount, sizeof(playersCount));
	players = new Player[playersCount];
	for (int i = 0; i < playersCount; i++)
	{
		players[i].deserialize(in);
	}
	return true;
}

void Game::writePlayers()
{

	std::ofstream out(fileName, std::ios::binary);
	if (!out) {
		std::cout << "Error";
	}

	out.write((char*)&playersCount, sizeof(playersCount));
	for (int i = 0; i < playersCount; i++)
	{
		players[i].serialize(out);
	}
}

int Game::includePlayer(const char* name)
{
	for (int i = 0; i < playersCount; i++)
	{
		if (strcmp(players[i].getName(), name) == 0) {
			return i;
		}
	}
	return -1;
}

void Game::setPlayer(char* name)
{
	int index = includePlayer(name);
	if (index != -1) {
		currentPlayer = players[index];
	}
	else {
		int age;
		do {
			std::cout << "Please enter your age: ";
			std::cin >> age;
			if (age < 18 || age > 90) {
				std::cout << "You have to be between 18 and 90 years." << std::endl;
			}
		} while (age < 18 || age>90);

		currentPlayer = Player(name, name, age);
	}
}

void Game::savePlayers()
{
	int index = includePlayer(currentPlayer.getName());
	if (index != -1) {
		players[index] = currentPlayer;
	}
	else {
		playersCount += 1;
		Player* newPlayers = new Player[playersCount];
		for (int i = 0; i < playersCount - 1; i++)
		{
			newPlayers[i] = players[i];
		}
		newPlayers[playersCount - 1] = currentPlayer;
		delete[] players;
		players = newPlayers;
	}

	writePlayers();
}



void Game::setDeck(char* token)
{
	char* series = strtok(token, " ");
	int deckCapacity = atoi(series);
	series = strtok(nullptr, " ");
	if (series) {
		deck = Deck(deckCapacity, series);
	}
	else {
		deck = Deck();
	}
}

const int Game::convertPoints(char* points) const
{
	if (strcmp(points, "J") == 0 || strcmp(points, "K") == 0 || strcmp(points, "Q") == 0) {
		return 10;
	}
	else if (strcmp(points, "A") == 0) {
		if (!isPlayerfinished) //The points which 'A' gives to  the player
		{
			if (currentPlayer.getCurrentPoints() <= 10) {
				return 11;
			}
			else {
				return 1;
			}
		} //The points which 'A' gives to the dealer
		else {
			if (dealerPoints + 11 > 21) {
				return 11;
			}
			return 1;
		}

	}
	else {
		return atoi(points);
	}
}

bool Game::checkName(char* name)
{
	char* token = strtok(name, " ");
	char firstName[LENGTH];
	strcpy(firstName, token);
	

	token = strtok(nullptr, " ");
	if (!token) {
		return false;
	}
	char secondName[LENGTH];
	strcpy(secondName, token);
	

	strcpy(name, firstName);
	strcat(name, " ");
	strcat(name, secondName);
	return true;
}
