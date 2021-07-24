#pragma once
#include <cstring>
#include <fstream>
#pragma warning(disable:4996)
class Player
{
public:
	Player(char* firstName, char* lastName, int age);
	Player();
	Player(const Player& other);
	Player& operator=(const Player& other);
	~Player();
	void deserialize(std::ifstream& in);
	void serialize(std::ofstream& out)const;

	void addCurrentPoints(const int points);
	void getWin(int games);
	void getLoose(int games);

	const char* getName() const { return name; };
	const int getCurrentPoints()const { return points; };

	friend std::ostream& operator<<(std::ostream& out, const Player& obj);

	bool validateAge(int age);

private:
	char* name;
	int age;
	int winnings;
	double qOfWinnings;
	unsigned int points;
	void copy(const Player& other);
	void clear();
};

