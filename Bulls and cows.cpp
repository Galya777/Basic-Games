#include <iostream>
#include <math.h>
#include <time.h>
using namespace std;
const int MASK = 0;
bool equal_digits(int number, int mask) {
	if (number == 0) {
		return false;
	}
	int lastdigit = 1 << (number % 10);
	if (mask & lastdigit) {
		return true;
	}
	return equal_digits(number / 10, mask | lastdigit);
}bool isThereZero(int number) {
	if (number < 1) {
		return false;
	}
	int num = number % 10;
	if (num == 0) {
		return true;
	}
	return isThereZero(number / 10);
}
bool validateTheNumber(int num) {
	if (num < 1000 || num>9999) {
		return false;
	}
	else if (num < 0) {
		return false;
	}
	else if (equal_digits(num, MASK) == true) {
		return false;
	}
	else if (isThereZero(num) == true) {
		return false;
	}
	return true;
}
int Bulls(int* secret, int* number,bool*sused,bool*nused) {
	int bulls = 0;
	for (int i = 0; i < 4; ++i) {
		if (secret[i] == number[i]) {
			bulls++;
			sused[i] = true;
			nused[i] = true;
		}
	}
	return bulls;
}int Cows(int*secret, int*number, bool* sused, bool* nused) {
	int cows = 0;
	for (int i = 0; i < 4; ++i) {
		for (int j = 0; j < 4; ++j) {
			if (sused[i] || nused[j])
				continue;
			if (secret[i] == number[j]) {
				cows++;
				sused[i] = true;
				nused[i] = true;
			}
		}
	}
	return cows;
}
int main()
{
	int secretNumber;
	srand(time(0));
	secretNumber = rand() % 10000;
	while (validateTheNumber(secretNumber) == false) {
		secretNumber = rand() % 10000;
		validateTheNumber(secretNumber);
	}
	//cout << secretNumber << endl;
	bool secret_used[4] = { false, false, false, false };
	bool number_used[4] = { false, false, false, false };
	int number;
		int tries = 0;
		do {
			cin >> number;
			while (validateTheNumber(number) == false) {
				cout << "Invalid number!" << endl;
				cin >> number;
				validateTheNumber(number);
			}
			int* num = new (nothrow) int[4];
			if (!num) {
				cout << "Not enough memory!";
				return 1;
			}
			int* sec = new(nothrow) int[4];
			if (!sec) {
				cout << "Not enough memory!";
				return 1;
			}
			for (int i = 0; i < 4; ++i) {
				int digit = pow(10, 4-i-1);
					num[i] = (number / digit)%10;
					
			}	cout << endl;
			for (int i = 0; i < 4; ++i) {
				int digit = pow(10, 4-i-1);
				sec[i] = (secretNumber / digit)%10;
				
			}cout << endl;
			cout << "Bulls are " << Bulls(sec,num,secret_used,number_used) << endl;
			cout << "Cows are " << Cows(sec,num,secret_used,number_used) << endl;
			++tries;
			delete[] num;
			delete[] sec;
		} while (number != secretNumber);
		cout << "You guessed it from the " << tries << " try!"<< endl;
	return 0;
}

