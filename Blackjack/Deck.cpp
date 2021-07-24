#include "Deck.h"
#pragma warning(disable:4996)
Deck::Deck(int k, char seria[9])
{
	cards = new Card[k];
	size = k;
	this->unique = THIS_ID;
	THIS_ID++;
	seria[0] = unique;
	strcpy(this->seria, seria);
	if (strcmp(seria, "") == 0) {
		strcpy(this->seria, "Custom");
	}
	int repeated = (k - 1) / 52 + 1;
	int counter = 0;
	for (int i = 0; i < k; ++i) {
		cards[i].generateRandomCard();
		if (repatingCard(cards[i])) {
			++counter;
		}else {
			counter = 0;
		}
		while (counter > repeated) {
			cards[i].generateRandomCard();
		}
	}
}

Deck::Deck()
{
	cards = new Card[52];
	size = 52;
	for (int i = 0; i < 52; ++i) {
		cards[i].generateRandomCard();
		cards[i].setSerial("default");
	}
}

Deck::~Deck()
{
	delete[] cards;
}

Card Deck::draw()
{
	std::swap(cards[0],cards[size]);
	return cards[size];
}

void Deck::swap(int a, int b)
{
	if ((a >= 0 && a < size) && (b >= 0 && b < size)) {
		std::swap(cards[a], cards[b]);
	}
}

int Deck::getSize()
{
	return size;
}

Card Deck::getCard(int i)
{
	return cards[i];
}

int Deck::suit_count(Color color)
{
	int colorcards = 0;
	for (int i = 0; i < size; ++i) {
		if (cards[i].getSuit() == color) {
			++colorcards;
		}
	}
	return colorcards;
}

int Deck::rank_count(char value)
{
	int rank = 0;
	for (int i = 0; i < size; ++i) {
		if (cards[i].getValue()==value) {
			++rank;
		}
	}
	return rank;
}

bool Deck::repatingCard(Card card)
{
	for (int i = 0; i < size; ++i) {
		if (card.getSuit() == cards[i].getSuit() && card.getValue() == cards[i].getValue()) {
			return true;
		}
	}
	return false;
}
