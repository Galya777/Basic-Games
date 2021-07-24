#pragma once
#include <ostream>

enum Color {
	NONE=-1,
	HEART,
	SPADES,
	DIAMONDS, 
	STARS
};
class Card
{
public:
	Card(Color col, char ser[15], char value);
	Card();

	bool validValue(char value);
	void setSerial(const char* s);
	void setSuit(int n);
	Color getSuit()const { return color; };
	const char getValue()const { return value; };
	void generateRandomCard();

private:
	Color color;
	char value;
	char serial[15];
	
	
	
};
std::ostream& operator<<(std::ostream& out, const Card& obj);
