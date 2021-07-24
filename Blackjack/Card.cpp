#include "Card.h"
#include <time.h>
#pragma warning(disable:4996)
const int VALUES_LENGTH = 13;
const int SUITS_LENGTH = 4;
std::ostream& operator<<(std::ostream& out, const Card& obj)
{
	out << obj.getValue() << "(" << obj.getSuit() << ")";
	return out;
}

Card::Card(Color col, char ser[15], char value)
{
	strcpy(serial, ser);
	color = col;
	this->value = value;
}



Card::Card()
{
	strcpy(serial, "");
	color = NONE;
	value = 1;
}

bool Card::validValue(char value)
{
	if (isdigit(value) == true && value != 1 && value<=10) {
		return true;
	}
	else if (value == 'K'|| value == 'Q'|| value == 'J'|| value == 'K') {
		return true;
	}
	return false;
}

void Card::setSerial(const char* s)
{
	strcpy(serial, s);
}

void Card::setSuit(int n)
{
	switch (n) {
	case 1: this->color = HEART;
		break;
	case 2: this->color = SPADES;
		break;
	case 3: this->color = DIAMONDS;
		break;
	case 4: this->color = STARS;
		break;
	default: this->color = NONE;
		break;
	}
}

void Card::generateRandomCard()
{
	int randomCol = rand() / SUITS_LENGTH;
	char randomValue = rand() / VALUES_LENGTH;
	setSuit(randomCol);
	while (validValue(randomValue) == false) {
		randomValue = rand() / VALUES_LENGTH;
	}
	this->value = randomValue;
}
