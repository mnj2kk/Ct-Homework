#include "phonebook.h"
#include <cstring>
#include <iostream>
bool operator<(const phonebook& l, const phonebook& r)
{
	int compLast = strcmp(l.lastName, r.lastName);

	if (compLast == 0)
	{
		int compN = strcmp(l.name, r.name);
		if (compN == 0)
		{
			int compMiddle = strcmp(l.middleName, r.middleName);
			if (compMiddle == 0)
			{
				return l.phone < r.phone;
			}
			return compMiddle < 0;
		}
		return compN < 0;
	}
	return compLast < 0;
}
bool operator>(const phonebook& l, const phonebook& r)
{
	return r < l;
}
std::istream& operator>>(std::istream& s, phonebook& p)
{
	s >> p.lastName >> p.name >> p.middleName >> p.phone;
	return s;
}
std::ostream& operator<<(std::ostream& s, phonebook& p)
{
	s << p.lastName << " " << p.name << " " << p.middleName << " " << p.phone;
	return s;
}
