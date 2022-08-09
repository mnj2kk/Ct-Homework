#include <iostream>
#include <string>
#include <string_view>
struct phonebook
{
	char name[21]{};
	char middleName[21]{};
	char lastName[21]{};
	long long int phone{};
};
bool operator<(const phonebook& l, const phonebook& r);
bool operator>(const phonebook& l, const phonebook& r);
std::istream& operator>>(std::istream& s, phonebook& p);
std::ostream& operator<<(std::ostream& s, phonebook& p);