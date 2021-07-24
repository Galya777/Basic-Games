#pragma once
#include "Card.h"
#include <cstring>
static int THIS_ID = 0;
class Deck
{
public:
	Deck(int k, char seria[9]);
	Deck();
	~Deck();
	Card draw();
	void swap(int a, int b);
	int getSize();
	Card getCard(int i);
	int suit_count(Color color);
	int rank_count(char value);
private:
	Card* cards;
	int size;
	char seria[10];
	int unique;
	bool repatingCard(Card card);
};

