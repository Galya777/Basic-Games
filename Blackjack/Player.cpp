#include "Player.h"

Player::Player(char* firstName, char* lastName, int age)
{
	name = new char[1000];
	this->name = strcat(firstName," ");
	this->name = strcat(name, lastName);
	this->age = age;
}

Player::Player()
{
	strcpy(name, "");
	age = 0;
	winnings = 0;
}

Player::Player(const Player& other)
{
	copy(other);
}

Player& Player::operator=(const Player& other)
{
	if (this != &other) {
		clear();
		copy(other);
	}

	return *this;
}

Player::~Player()
{
	clear();
}

void Player::deserialize(std::ifstream& in)
{
	clear();
	int len;
	in.read((char*)&len, sizeof(len));
	name = new char[len];
	in.read((char*)name, len);
	in.read((char*)&age, sizeof(age));
	in.read((char*)&winnings, sizeof(winnings));
	in.read((char*)&qOfWinnings, sizeof(qOfWinnings));
	points = 0;
}

void Player::serialize(std::ofstream& out) const
{
	int len = strlen(name) + 1;
	out.write((char*)&len, sizeof(len));
	out.write((char*)name, len);
	out.write((char*)&age, sizeof(age));
	out.write((char*)&winnings, sizeof(winnings));
	out.write((char*)&qOfWinnings, sizeof(qOfWinnings));
}

void Player::addCurrentPoints(const int points)
{
	this->points += points;
}

void Player::getWin(int games)
{
	winnings += 1;
	qOfWinnings = winnings / (double)games;
}

void Player::getLoose(int games)
{
	qOfWinnings = winnings / (double)games;
}

bool Player::validateAge(int age)
{
	if (age >= 19 && age <= 90) {
		return true;
	}
	return false;
}

void Player::copy(const Player& other)
{
	name = new char[strlen(other.name) + 1];
	strcpy(name, other.name);
	age = other.age;
	winnings = other.winnings;
	qOfWinnings = other.qOfWinnings;
	points = other.points;
}

void Player::clear()
{
	delete[] name;
}

std::ostream& operator<<(std::ostream& out, const Player& obj)
{
	out << obj.name << " " << obj.winnings << " " << obj.qOfWinnings << std::endl;
	return out;
}
