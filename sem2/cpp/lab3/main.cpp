#include "phonebook.h"
#include "quicksort.h"
#include "return_codes.h"
#include <fstream>

#include <new>
#include <string>
#define FLOAT		  "float"
#define INT			  "int"
#define PHONEBOOK	  "phonebook"
#define NO_INPUT	  "no input/output files specified"
#define OUT_OF_MEMORY "no enough  memory"
#define FILE_NOT_OPEN "couldn't open the file"
#define ASCENDING	  "ascending"
template< typename T >
int solve(std::ifstream& in, char* outFile, size_t n, std::string& mode)
{
	auto* arr = new (std::nothrow) T[n];
	if (arr == nullptr)
	{
		std::cerr << OUT_OF_MEMORY;
		in.close();
		return ERROR_OUTOFMEMORY;
	}
	for (size_t i = 0; i < n; ++i)
	{
		in >> arr[i];
	}
	if (n != 0)
	{
		(mode == ASCENDING) ? quicksort< T, false >(arr, n, 0, n - 1) : quicksort< T, true >(arr, n, 0, n - 1);
	}
	std::ofstream out(outFile);
	if (!out.is_open())
	{
		std::cerr << FILE_NOT_OPEN;
		in.close();
		return ERROR_FILE_NOT_FOUND;
	}

	for (size_t i = 0; i < n; ++i)
	{
		out << arr[i] << "\n";
	}
	delete[] arr;
	in.close();
	out.close();
	return ERROR_SUCCESS;
}
int main(int argc, char* argv[])
{
	if (argc < 3)
	{
		std::cerr << NO_INPUT;
		return ERROR_INVALID_PARAMETER;
	}
	std::ifstream in(argv[1], std::ios_base::in);
	if (!in.is_open())
	{
		std::cerr << FILE_NOT_OPEN;
		return ERROR_FILE_NOT_FOUND;
	}
	std::string type, mode;
	size_t n;
	in >> type >> mode >> n;
	if (type == INT)
	{
		return solve< int >(in, argv[2], n, mode);
	}
	if (type == FLOAT)
	{
		return solve< float >(in, argv[2], n, mode);
	}
	else
	{
		return solve< phonebook >(in, argv[2], n, mode);
	}
}
