#include <LN.h>
// Inner operation realisation
char* reverse(char* arr, size_t start, size_t length)
{
	char* t = new char[length - start];
	for (size_t i = start; i < length; i++)
	{
		t[length - 1 - i] = arr[i];
	}
	return t;
}
size_t removeTrailingZeroes(const char* points, size_t l)
{
	for (size_t i = l; i > 0; i--)
	{
		if (points[i - 1] != 0 && points[i - 1] != '0')
		{
			return i;
		}
	}
	return 1;
}
int LN::compare(const LN& a, const LN& b)
{
	if (a.sign != b.sign && a.length == 1 && b.length == 1 && a.points[0] == b.points[0] && a.points[0] == '0')
	{
		return 0;
	}
	if (!a.sign && b.sign)
	{
		return 1;
	}
	else if (!b.sign && a.sign)
	{
		return -1;
	}
	else
	{
		bool sign = a.sign;
		if (a.length > b.length)
		{
			return sign ? -1 : 1;
		}
		else if (a.length < b.length)
		{
			return sign ? 1 : -1;
		}
		else
		{
			bool aIsBigger = false;
			bool bIsBigger = false;
			for (size_t i = a.length; i > 0; i--)
			{
				if (a.points[i - 1] > b.points[i - 1])
				{
					aIsBigger = true;
					break;
				}

				if (a.points[i - 1] < b.points[i - 1])
				{
					bIsBigger = true;
					break;
				}
			}
			if (aIsBigger)
			{
				return sign ? -1 : 1;
			}
			if (bIsBigger)
			{
				return sign ? 1 : -1;
			}
			return 0;
		}
	}
}
int LN::moduleCompare(const LN& a, const LN& b)
{
	if (a.length > b.length)
	{
		return 1;
	}
	else if (a.length < b.length)
	{
		return -1;
	}
	else
	{
		bool aIsBigger = false;
		bool bIsBigger = false;
		for (size_t i = a.length; i > 0; i--)
		{
			if (a.points[i - 1] > b.points[i - 1])
			{
				aIsBigger = true;
				break;
			}

			if (a.points[i - 1] < b.points[i - 1])
			{
				bIsBigger = true;
				break;
			}
		}
		if (aIsBigger)
		{
			return 1;
		}
		if (bIsBigger)
		{
			return -1;
		}
		return 0;
	}
}
char* LN::add(LN& a, LN& b, size_t& l)
{
	size_t al = a.length;
	size_t bl = b.length;
	size_t length = std::max(al, bl) + 1;
	char* points = new char[length]();
	int rest = 0;
	for (size_t i = 0; i < length; i++)
	{
		int c = (i < al ? (int)a.points[i] - '0' : 0) + (i < bl ? (int)b.points[i] - '0' : 0) + rest;
		points[i] = (char)((c % 10) + '0');
		rest = c >= 10 ? 1 : 0;
	}
	l = removeTrailingZeroes(points, length);
	return points;
}
char* LN::subtract(LN& a, LN& b, size_t& l)
{
	size_t al = a.length;
	size_t bl = b.length;
	size_t length = std::max(al, bl) + 1;
	char* points = new char[length]();
	int rest = 0;
	for (size_t i = 0; i < length; i++)
	{
		int c = (i < al ? (int)a.points[i] - '0' : 0) - (i < bl ? (int)b.points[i] - '0' : 0) - rest;
		points[i] = (char)(((10 + c) % 10) + '0');
		rest = c < 0 ? 1 : 0;
	}
	l = removeTrailingZeroes(points, length);
	return points;
}
char* LN::multiply(const char* a, size_t alength, const char* b, size_t blength, size_t& l)
{
	char* r = new char[alength + blength + 1];
	for (int i = 0; i < alength + blength + 1; ++i)
	{
		r[i] = '0';
	}
	for (size_t i = 0; i < alength; i++)
	{
		int rest = 0;
		for (size_t j = 0; j < blength || rest != 0; j++)
		{
			int d = (r[i + j] - '0' + (a[i] - '0') * (j < blength ? (b[j] - '0') : 0) + rest);
			r[i + j] = (char)((d % 10) + '0');
			rest = d / 10;
		}
	}
	l = removeTrailingZeroes(r, alength + blength + 1);
	return r;
}
char* LN::divide(LN& a, LN& b, size_t& l)
{
	char* res = new char[a.length]();
	LN c = LN{ false, res, a.length };
	LN m;
	for (size_t i = 0; i < a.length; i++)
	{
		res[i] = '0';
	}
	for (size_t i = a.length; i > 0; i--)
	{
		for (int j = 1; j < 10; j++)
		{
			res[i - 1] = (char)(j + '0');

			m = b * c;
			c.length = removeTrailingZeroes(c.points, c.length);
			int x = moduleCompare(m, a);
			if (x == 0)
			{
				l = removeTrailingZeroes(res, a.length);
				c.points = nullptr;
				return res;
			}
			if (x == 1)
			{
				res[i - 1] = (char)(j - 1 + '0');
				break;
			}
		}
	}
	l = removeTrailingZeroes(res, a.length);
	c.points = nullptr;
	return res;
}
LN LN::sqrt(LN& a)
{
	char* res = new char[a.length]();
	LN c = LN{ false, res, a.length };
	LN m;
	for (size_t i = 0; i < a.length; i++)
	{
		res[i] = '0';
	}
	for (size_t i = a.length; i > 0; i--)
	{
		for (int j = 1; j < 10; j++)
		{
			res[i - 1] = (char)(j + '0');

			m = c * c;
			c.length = removeTrailingZeroes(c.points, c.length);
			int x = moduleCompare(m, a);
			if (x == 0)
			{
				c.length = removeTrailingZeroes(res, a.length);
				return c;
			}
			if (x == 1)
			{
				res[i - 1] = (char)(j - 1 + '0');
				break;
			}
		}
	}
	c.length = removeTrailingZeroes(res, a.length);
	return c;
}

LN operator>(LN& a, LN& b)
{
	if (a.isNaN || b.isNaN)
	{
		return LN(0ll);
	}
	return LN(LN::compare(a, b) == 1 ? 1 : 0);
}
LN operator>=(LN& a, LN& b)
{
	if (a.isNaN || b.isNaN)
	{
		return LN(0ll);
	}
	int v = LN::compare(a, b);
	return LN((v == 0 || v == 1) ? 1 : 0);
}
LN operator==(const LN& a, const LN& b)
{
	if (a.isNaN || b.isNaN)
	{
		return LN(0ll);
	}
	return LN(LN::compare(a, b) == 0 ? 1 : 0);
}
LN operator!=(LN& a, LN& b)
{
	if (a.isNaN || b.isNaN)
	{
		return LN(1ll);
	}
	return LN(LN::compare(a, b) != 0 ? 1 : 0);
}
LN operator<(LN & a, LN& b)
{
	if (a.isNaN || b.isNaN)
	{
		return LN(0ll);
	}
	return LN(LN::compare(a, b) == -1 ? 1 : 0);
}
LN operator<=(LN& a, LN& b)
{
	if (a.isNaN || b.isNaN)
	{
		return LN(0ll);
	}
	int v = LN::compare(a, b);
	return LN((v == 0 || v == -1) ? 1 : 0);
}

LN operator+(LN& a, LN& b)
{
	if (a.isNaN || b.isNaN)
	{
		return { true, nullptr, 0, true };
	}
	if (a.sign == b.sign)
	{
		size_t l = 0;
		char* points = LN::add(a, b, l);
		bool sign = a.sign;
		return { sign, points, l };
	}
	if (!a.sign && b.sign)
	{
		b.sign = false;
		LN r = a - b;
		b.sign = true;
		return r;
	}
	else
	{
		a.sign = false;
		LN r = b - a;
		a.sign = true;
		return r;
	}
}
LN operator-(LN& a, LN& b)
{
	if (a.isNaN || b.isNaN)
	{
		return { true, nullptr, 0, true };
	}
	size_t l = 0;
	if (a.sign == b.sign)
	{
		if (LN::moduleCompare(a, b) != -1)
		{
			return { a.sign, LN::subtract(a, b, l), l };
		}
		else
		{
			return { !a.sign, LN::subtract(b, a, l), l };
		}
	}
	else if (!a.sign && b.sign)
	{
		b.sign = false;
		LN r = a + b;
		b.sign = true;
		r.sign = false;
		return r;
	}
	else
	{
		a.sign = false;
		LN r = a + b;
		a.sign = true;
		r.sign = true;
		return r;
	}
}
LN operator*(LN& a, LN& b)
{
	if (a.isNaN || b.isNaN)
	{
		return { true, nullptr, 0, true };
	}
	size_t l = 0;
	char* points = LN::multiply(a.points, a.length, b.points, b.length, l);
	return { a.sign != b.sign, points, l };
}
LN operator/(LN& a, LN& b)
{
	if (!b || a.isNaN || b.isNaN)
	{
		return { true, nullptr, 0, true };
	}
	size_t l;
	char* points = LN::divide(a, b, l);
	return { a.sign != b.sign, points, l };
}

LN& LN::operator+=(LN& other)
{
	*this = *this + other;
	return *this;
}
LN& LN::operator-=(LN& other)
{
	*this = *this - other;
	return *this;
}
LN& LN::operator*=(LN& other)
{
	*this = *this * other;
	return *this;
}
LN& LN::operator/=(LN& other)
{
	*this = *this / other;
	return *this;
}

LN operator-(LN& ln)
{
	if (ln.isNaN)
	{
		return { !ln.sign, nullptr, ln.length, true };
	}
	char* tmp = new char[ln.length];
	memcpy(tmp, ln.points, ln.length);
	return { !ln.sign, tmp, ln.length };
}
LN operator%(LN& a, LN& b)
{
	LN c = a / b;
	LN d = c * b;
	return a - d;
}
LN operator~(LN& a)
{
	if (a.isNaN || (a.sign && LN::compare(a, LN(0ll)) != 0))
	{
		return { false, nullptr, 0, true };
	}
	return LN::sqrt(a);
}
std::ostream& operator<<(std::ostream& s, LN& ln)
{
	if (ln.isNaN)
	{
		s << "NaN";
		return s;
	}
	if (ln.length == 1 && LN::moduleCompare(ln, LN(0ll)) == 0)
	{
		s << 0;
		return s;
	}
	if (ln.sign)
	{
		s << "-";
	}
	for (size_t i = ln.length; i > 0; i--)
	{
		s << ln.points[i - 1];
	}
	return s;
}
// Constructors
LN::LN(char* number)
{
	if (strcmp(number, "NaN") == 0)
	{
		this->isNaN = true;
		this->points = nullptr;
		this->length = 0;
		this->sign = false;
		return;
	}
	this->sign = number[0] == '-';
	if (this->sign)
	{
		this->length = strlen(number) - 1;
		number = reverse(number, 1, this->length + 1);
		this->points = number;
	}
	else
	{
		this->length = strlen(number);
		this->points = reverse(number, 0, this->length);
	}
	this->length = removeTrailingZeroes(this->points, this->length);
}
LN::LN(std::string_view number)
{
	if (number == "NaN")
	{
		this->isNaN = true;
		this->points = nullptr;
		this->length = 0;
		this->sign = false;
		return;
	}
	this->sign = number[0] == '-';
	char* tmp;
	if (this->sign)
	{
		this->length = number.length() - 1;
		tmp = new char[length];
		number.copy(tmp, number.length(), 1);
	}
	else
	{
		this->length = number.length();
		tmp = new char[length];
		number.copy(tmp, number.length(), 0);
	}
	this->points = reverse(tmp, 0, this->length);
	this->length = removeTrailingZeroes(this->points, this->length);
	delete[] tmp;
}
LN::LN(long long int number)
{
	this->sign = (number < 0);
	char* tmp = new char[20];
	sprintf(tmp, "%lld", std::abs(number));
	this->length = strlen(tmp);
	;
	this->points = reverse(tmp, 0, this->length);
	delete[] tmp;
}
LN::LN(bool sign, char* points, size_t l, bool isNaN)
{
	this->sign = sign;
	this->points = points;
	this->length = l;
	this->isNaN = isNaN;
}

LN::LN(const LN& ln)
{
	this->length = ln.length;
	this->points = new char[this->length];
	memcpy(this->points, ln.points, this->length);
	this->isNaN = ln.isNaN;
	this->sign = ln.sign;
}
LN::LN(LN&& ln) noexcept
{
	sign = ln.sign;
	isNaN = ln.isNaN;
	length = ln.length;
	points = ln.points;
	ln.points = nullptr;
}
LN& LN::operator=(const LN& other)
{
	if (&other != this)
	{
		this->sign = other.sign;
		this->length = other.length;
		this->isNaN = other.isNaN;
		delete[] this->points;
		this->points = new char[other.length];
		memcpy(this->points, other.points, other.length);
	}
	return *this;
}
LN& LN::operator=(LN&& other) noexcept
{
	if (&other != this)
	{
		this->sign = other.sign;
		this->length = other.length;
		this->isNaN = other.isNaN;
		delete[] this->points;
		this->points = other.points;
		other.points = nullptr;
	}
	return *this;
}
LN::~LN()
{
	delete[] points;
}

LN::operator bool() const
{
	return LN::compare(*this, LN(0ll)) != 0;
}
LN::operator llu()
{
	LN LLU_MAX = LN("9223372036854775808");
	LN LLU_MIN = LN(" -9223372036854775809");
	llu l;
	if (sign && moduleCompare(*this, LLU_MAX) == 1)
	{
		char* tmp = reverse(points, 0, length);
		l = -atoi(tmp);
		delete[] tmp;
	}
	else if (!sign && moduleCompare(*this, LLU_MIN) == -1)
	{
		char* tmp = reverse(points, 0, length);
		l = atoi(tmp);
		delete[] tmp;
	}
	else
	{
		throw std::overflow_error("couldn't convert LN to long long int");
	}
	return l;
}
LN operator"" _ln(const char* ch)
{
	return LN(ch);
}
