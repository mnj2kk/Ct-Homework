#include <string_view>

#include <cstring>
#include <iostream>
typedef long long llu;

class LN
{
	char* points;
	bool sign;
	size_t length;
	bool isNaN = false;

	static char* add(LN&, LN&, size_t&);
	static char* subtract(LN&, LN&, size_t&);
	static char* multiply(const char*, size_t, const char*, size_t, size_t&);
	static char* divide(LN& a, LN& b, size_t& l);
	static int compare(const LN&, const LN&);
	static int moduleCompare(const LN&, const LN&);
	static LN sqrt(LN& a);

	friend LN operator+(LN&, LN&);
	friend LN operator-(LN&, LN&);
	friend LN operator/(LN&, LN&);
	friend LN operator*(LN&, LN&);

	LN& operator+=(LN& other);
	LN& operator-=(LN& other);
	LN& operator*=(LN& other);
	LN& operator/=(LN& other);

	LN& operator=(const LN&);
	LN& operator=(LN&& other) noexcept;
	friend LN operator~(LN&);
	friend LN operator%(LN&, LN&);
	friend LN operator-(LN&);

	friend LN operator>(LN&, LN&);
	friend LN operator>=(LN&, LN&);
	friend LN operator==(const LN&, const LN&);
	friend LN operator!=(LN&, LN&);
	friend LN operator<=(LN&, LN&);
	friend LN operator<(LN&, LN&);

	friend std::ostream& operator<<(std::ostream& s, LN& ln);

  public:
	LN(bool, char*, size_t, bool isNaN = false);
	explicit LN(std::string_view);
	explicit LN(char*);
	explicit LN(long long int = 0);
	LN(const LN&);
	~LN();
	LN(LN&&) noexcept;
	explicit operator bool() const;
	explicit operator llu();
};
LN operator"" _ln(const char*);
