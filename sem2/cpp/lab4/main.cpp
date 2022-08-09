#include "LN.h"
#include "return_codes.h"

#include <fstream>
#include <functional>
#include <iostream>
#include <map>
#include <stack>
#define FILE_NOT_OPEN "couldn't open the file"
#define INVALID_PARAMETER "no enough parameters"
#define OUT_OF_MEMORY "out of memory"
int main(int argc, char* argv[])
{
	if (argc < 3)
	{
		std::cerr << INVALID_PARAMETER;
		return ERROR_INVALID_PARAMETER;
	}
	std::ifstream in(argv[1], std::ios_base::in);
	if (!in.is_open())
	{
		std::cerr << FILE_NOT_OPEN;
		return ERROR_FILE_NOT_FOUND;
	}
	std::stack< LN > stack;
	std::map< std::string, std::function< LN(LN&, LN&) > > OPERATORS_MAP{
		{ "+", std::plus() },
		{ "-", std::minus() },
		{ "*", std::multiplies() },
		{ "/", std::divides() },

		{ "+=", std::plus() },
		{ "--", std::minus() },
		{ "*=", std::multiplies() },
		{ "/=", std::divides() },

		{ ">", std::greater() },
		{ ">=", std::greater_equal() },
		{ "!=", std::not_equal_to() },
		{ "==", std::equal_to() },
		{ "<=", std::less_equal() },
		{ "<", std::less() },
		{ "%", std::modulus() },
		// UNARY_FUNCTIONS
		{ "~", std::equal_to() },
		{ "_", std::equal_to() }

	};
	std::map< std::string, std::function< LN(LN&) > > UNARY_OPERATORS_MAP{ { "~", std::bit_not() }, { "_", std::negate() }

	};
	std::string tmp;

	while (in >> tmp)
	{
		auto function = OPERATORS_MAP.find(tmp);
		if (function != std::end(OPERATORS_MAP))
		{
			LN b = stack.top();
			stack.pop();
			auto y = UNARY_OPERATORS_MAP.find(tmp);
			if (y != std::end(UNARY_OPERATORS_MAP))
			{
				try
				{
					stack.push(y->second(b));
				} catch (std::bad_alloc& e)
				{
					std::cerr << OUT_OF_MEMORY;
					return ERROR_OUTOFMEMORY;
				}
				continue;
			}
			LN a = stack.top();
			stack.pop();
			try
			{
				stack.push(((function->second)(a, b)));
			} catch (std::bad_alloc& e)
			{
				std::cerr << OUT_OF_MEMORY;
				return ERROR_OUTOFMEMORY;
			}
		}
		else
		{
			try
			{
				stack.push(LN(tmp));
			} catch (std::bad_alloc& e)
			{
				std::cerr << OUT_OF_MEMORY;
				return ERROR_OUTOFMEMORY;
			}
		}
	}

	in.close();
	std::ofstream out(argv[2]);
	if (!out.is_open())
	{
		std::cerr << FILE_NOT_OPEN;
		return ERROR_FILE_NOT_FOUND;
	}
	while (!stack.empty())
	{
		out << stack.top() << "\n";
		stack.pop();
	}
	out.close();
	return ERROR_SUCCESS;
}