#include "return_codes.h"

#include <float.h>
#include <math.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#define EPS 1E-5f
//#define DEBUG
#define MAX(a, b) (((a) > (b)) ? (a) : (b))
#define MIN(a, b) (((a) < (b)) ? (a) : (b))
bool areEqual(float a, float b)
{	 // https://stackoverflow.com/questions/4915462/how-should-i-do-floating-point-comparison idea taken from this
	float am = fabsf(a);
	float bm = fabsf(b);
	float diff = fabsf(a - b);
	if (diff < MAX((MIN(am + bm, FLT_MAX)) * FLT_EPSILON * 128, FLT_MIN))
	{
		return true;
	}

	return false;
}
void printArr(size_t n, float** matrix)
{
	for (size_t i = 0; i < n; ++i)
	{
		for (size_t j = 0; j < n + 1; ++j)
		{
			printf("%f ", matrix[i][j]);
		}
		printf("\n");
	}
	printf("\n");
}
void swap(size_t n, float** matrix, size_t start, size_t end, size_t base)
{
	float* max = matrix[start];
	size_t index = start;
	for (size_t i = start; i < end; ++i)
	{
		if (fabsf(max[base]) < fabsf(matrix[i][base]))
		{
			max = matrix[i];
			index = i;
		}
	}
	float* tmp = max;
	matrix[index] = matrix[start];
	matrix[start] = tmp;
}

void freeMatrix(size_t n, float** matrix)
{
	for (size_t i = 0; i < n; ++i)
	{
		if (matrix[i])
		{
			free(matrix[i]);
		}
	}
	free(matrix);
}

void gauss(size_t n, float** matrix)
{
	for (int i = 0; i < n - 1; ++i)
	{
		swap(n, matrix, i, n, i);
		if (matrix[i][i] != 0)
		{
			for (int j = i + 1; j < n; ++j)
			{
				if (matrix[j][i] != 0)
				{
					float coef = matrix[j][i] / matrix[i][i];
					for (size_t k = i + 1; k < n + 1; ++k)
					{
						if (areEqual(matrix[j][k], matrix[i][k] * coef))
						{
							matrix[j][k] = 0.0f;
						}
						else
						{
							matrix[j][k] -= matrix[i][k] * coef;
						}
					}
					matrix[j][i] = 0.0f;
				}
			}
		}
	}
}
int printNotOneSolution(int c, char* file)
{
	FILE* output = fopen(file, "w");
	if (output == NULL)
	{
		printf("couldn't create/open output file");
		return ERROR_PATH_NOT_FOUND;
	}
	if (c)
	{
		fprintf(output, "many solutions");
	}
	else
	{
		fprintf(output, "no solution");
	}
	fclose(output);
	return ERROR_SUCCESS;
}
int printSolution(float* solution, size_t n, char* file)
{
	FILE* output = fopen(file, "w");
	if (output == NULL)
	{
		printf("couldn't create/open output file");
		return ERROR_PATH_NOT_FOUND;
	}
	for (size_t i = 0; i < n; i++)
	{
		fprintf(output, "%g \n", solution[i]);
	}
	fclose(output);
	return ERROR_SUCCESS;
}
int backSubstitution(size_t n, float* solution, float** matrix, char* file)
{
	bool hasZeroLine = false;
	for (size_t i = 0; i < n; i++)
	{
		bool hasOnlyZeroes = true;
		for (size_t j = 0; j < n; ++j)
		{
			if (matrix[i][j] != 0)
			{
				hasOnlyZeroes = false;
				break;
			}
		}
		if (hasOnlyZeroes)
		{
			if (matrix[i][n] != 0)
			{
				return printNotOneSolution(0, file);
			}
			hasZeroLine = true;
		}
	}
	if (hasZeroLine)
	{
		return printNotOneSolution(1, file);
	}
	for (size_t i = n; i-- > 0;)
	{
		double res = (double)matrix[i][n];
		for (size_t j = n - 1; j > i; j--)
		{
			if (matrix[i][j] != 0)
			{
				res -= (double)(matrix[i][j]) * (double)solution[j];
			}
		}
		if (matrix[i][i] != 0.0f)
		{
			solution[i] = (float)(res / matrix[i][i]);
		}
		else
		{
			int code;
			if (fabs(res) == 0.0f)
			{
				code = printNotOneSolution(1, file);
			}
			else
			{
				code = printNotOneSolution(0, file);
			}
			return code;
		}
	}
	return (printSolution(solution, n, file));
}

int main(int argc, char* argv[])
{
	if (argc < 3)
	{
		printf("no output/input files specified");
		return ERROR_INVALID_PARAMETER;
	}
	FILE* input = fopen(argv[1], "r");
	if (!input)
	{
		printf("couldn't open the file");
		return ERROR_FILE_NOT_FOUND;
	}
	size_t n;
	fscanf(input, "%zi", &n);
	float** matrix = malloc(sizeof(float*) * n);
	if (!matrix)
	{
		fclose(input);
		printf("no enough memory");
		return ERROR_NOT_ENOUGH_MEMORY;
	}
	else
	{
		for (size_t i = 0; i < n; i++)
		{
			matrix[i] = malloc(sizeof(float) * (n + 1));
			if (!matrix[i])
			{
				fclose(input);
				freeMatrix(n, matrix);
				printf("no enough memory");
				return ERROR_NOT_ENOUGH_MEMORY;
			}
			for (size_t j = 0; j < n + 1; j++)
			{
				fscanf(input, "%f ", &matrix[i][j]);
			}
		}
	}
	fclose(input);
	gauss(n, matrix);
#if defined(DEBUG)
	printArr(n, matrix);
#endif

	float* solution = (float*)malloc(sizeof(float) * n);
	if (!solution)
	{
		freeMatrix(n, matrix);
		printf("not enough memory");
		return ERROR_NOT_ENOUGH_MEMORY;
	}
	int code = backSubstitution(n, solution, matrix, argv[2]);
	free(solution);
	freeMatrix(n, matrix);
	return code;
}
