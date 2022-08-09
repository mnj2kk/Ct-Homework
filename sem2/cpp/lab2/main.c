#include "return_codes.h"
#include <ctype.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
typedef unsigned char uchar;
typedef unsigned int uint;
typedef union uchar2int
{
	uchar ch[4];
	int i;
} uchar2int;
typedef struct chunk
{
	int length;
	uchar type[4];
	uint crc;
	uchar data[];

} chunk;
uchar paeth(int a, int b, int c)
{
	int p = a + b - c;
	int pa = abs(p - a);
	int pb = abs(p - b);
	int pc = abs(p - c);
	if (pa <= pb && pa <= pc)
	{
		return a;
	}
	else if (pb <= pc)
	{
		return b;
	}
	else
	{
		return c;
	}
}
bool check(const uchar ch[4], const uchar s[4])
{
	for (int i = 0; i < 4; i++)
	{
		if (ch[i] != s[i])
		{
			return false;
		}
	}
	return true;
}
bool checkType(const uchar ch[4], const int t)
{
	uchar s[4];
	if (t == 0)
	{
		uchar ta[4] = { 73, 69, 78, 68 };
		return check(ch, ta);
	}
	else if (t == 1)
	{
		uchar ta[4] = { 73, 72, 68, 82 };
		return check(ch, ta);
	}
	else if (t == 2)
	{
		uchar ta[4] = { 73, 68, 65, 84 };
		return check(ch, ta);
	}
	else if (t == 3)
	{
		uchar ta[4] = { 'P', 'L', 'T', 'E' };
		return check(ch, ta);
	}

	return true;
}
void filter(uchar* res, int h, int w, int d, FILE* out)
{
	int w1 = d * w + 1;
	for (int i = 0; i < h; ++i)
	{
		uchar filter = res[i * w1];
		for (int j = 1; j < w1; j++)
		{
			if (filter == 1 && j > d)
			{
				res[w1 * i + j] += res[w1 * i + j - d];
			}
			else if (filter == 2 && i > 0)
			{
				res[w1 * i + j] += res[w1 * (i - 1) + j];
			}
			else if (filter == 3 && i > 0 && j > d)
			{
				res[w1 * i + j] += ((int)res[w1 * (i - 1) + j] + (int)res[w1 * i + j - d]) / 2;
			}
			else if (filter == 3 && i > 0 && j <= d)
			{
				res[w1 * i + j] += ((int)res[w1 * (i - 1) + j]) / 2;
			}
			else if (filter == 4 && i > 0 && j > d)
			{
				res[w1 * i + j] += paeth(res[w1 * i + j - d], res[w1 * (i - 1) + j], res[w1 * (i - 1) + j - d]);
			}

			else if (filter == 4 && i > 0 && j <= d)
			{
				res[w1 * i + j] += res[w1 * (i - 1) + j];
			}
			else if (filter == 4)
			{
				res[w1 * i + j] += res[w1 * i + j - d];
			}
		}
	}
	for (int i = 0; i < h; ++i)
	{
		fwrite(res + (i * w1) + 1, sizeof(uchar), w1 - 1, out);
	}
}
bool checkThatIsACorrectChunk(chunk* ch, uchar t)
{
	if (!isalpha(ch->type[0]) || !isalpha(ch->type[1]) || !isalpha(ch->type[2]) || !isalpha(ch->type[3]))
	{
		return false;
	}
	if ((isupper(ch->type[0]) && ((t != 3 && !(checkType(ch->type, 2))) || !(checkType(ch->type, 2) || checkType(ch->type, 3)))) ||
		islower(ch->type[2]))
	{
		return false;
	}
	return true;
}
#if defined(ZLIB)
	#include <zlib.h>
uchar* decompress(uchar* ch, size_t size, uchar* res, size_t decompressSize)
{
	int err = uncompress(res, &decompressSize, ch, size);
	if (err != Z_OK)
	{
		return NULL;
	}
	return res;
}
#elif defined(LIBDEFLATE)

	#include <libdeflate.h>
uchar* decompress(uchar* ch, size_t size, uchar* res, size_t decompressSize)
{
	struct libdeflate_decompressor* decompressor = libdeflate_alloc_decompressor();
	if (decompressor == NULL)
	{
		return NULL;
	}
	size_t t;
	int err = libdeflate_zlib_decompress(decompressor, ch, size, res, decompressSize, &t);
	if (err != LIBDEFLATE_SUCCESS)
	{
		return NULL;
	}
	return res;
}
#elif defined(ISAL)

	#include <include/igzip_lib.h>
uchar* decompress(uchar* ch, size_t size, uchar* res, size_t decompressSize)
{
	struct inflate_state state;
	isal_inflate_init(&state);
	state.next_in = ch;
	state.avail_in = size;
	state.next_out = res;
	state.crc_flag = ISAL_ZLIB;
	state.avail_out = decompressSize;
	int err = isal_inflate(&state);
	if (err != ISAL_DECOMP_OK)
	{
		return NULL;
	}
	return res;
}
#else
	#error "unsupported lib"
uchar* decompress(uchar* ch, size_t size, uchar* res, size_t decompressSize)
{
	return res;
}
#endif
// Как жи хочется C23 с #elifdef
bool checkSignature(FILE* in)
{
	uchar sign[8];
	// Values from the standard
	uchar defSign[8] = { 137, 80, 78, 71, 13, 10, 26, 10 };
	fread(sign, sizeof *sign, 8, in);
	for (int i = 0; i < 8; i++)
	{
		if (sign[i] != defSign[i])
		{
			return false;
		}
	}
	return true;
}
bool checkCRC(chunk* ch)
{
	return true;
}	 // png is big endian so we must convert
// https://stackoverflow.com/questions/2182002/convert-big-endian-to-little-endian-in-c-without-using-provided-func
int swap_int32(int val)
{
	val = ((val << 8) & 0xFF00FF00) | ((val >> 8) & 0xFF00FF);
	return (val << 16) | ((val >> 16) & 0xFFFF);
}
chunk* readChunk(FILE* in)
{
	int tmp;
	if (fread(&tmp, sizeof(tmp), 1, in) < 1)
	{
		return NULL;
	}
	tmp = swap_int32(tmp);
	if (tmp < 0)
	{
		return NULL;
	}
	chunk* ch = malloc(sizeof(*ch) + sizeof(uchar) * tmp);
	if (ch == NULL)
	{
		return NULL;
	}
	ch->length = tmp;
	if (fread(&(ch->type), sizeof(uchar), 4, in) < 4)
	{
		return NULL;
	}
	if (fread(&(ch->data), sizeof(uchar), ch->length, in) < ch->length)
	{
		return NULL;
	}
	if (fread(&(ch->crc), sizeof(uint), 1, in) < 1 || !checkCRC(ch))
	{
		return NULL;
	}
	return ch;
}

bool ihdrCheck(chunk* ihdr)
{
	if (ihdr->length != 13 || ihdr->data[10] != 0 || ihdr->data[11] != 0 || ihdr->data[12] > 1)
	{
		return false;
	}
	if ((ihdr->data[9] == 0 &&
		 (ihdr->data[8] == 1 || ihdr->data[8] == 2 || ihdr->data[8] == 4 || ihdr->data[8] == 8 || ihdr->data[8] == 16)) ||
		(ihdr->data[9] == 2 && (ihdr->data[8] == 8 || ihdr->data[8] == 16)))
	{
		return true;
	}
	return false;
}
void printTitle(FILE* out, chunk* ihdr, int* w, int* h, int* d)
{
	char s[3] = { 'P', 'T', '\n' };
	if (ihdr->data[9] == 2)
	{
		*d = 3;
		s[1] = '6';
	}
	else
	{
		*d = 1;
		s[1] = '5';
	}
	fwrite(&s, sizeof(char), 3, out);
	uchar2int con;
	memcpy(con.ch, &ihdr->data, 4 * sizeof(uchar));
	*w = swap_int32(con.i);
	memcpy(con.ch, &ihdr->data[4], 4 * sizeof(uchar));
	*h = swap_int32(con.i);
	int c = (1 << ihdr->data[8]) - 1;
	char buf[32];
	int n = sprintf(buf, "%d %d\n%d\n", *w, *h, c);
	fwrite(&buf, sizeof(char), n, out);
}
int main(int argc, char* argv[])
{
	if (argc < 3)
	{
		fprintf(stderr, "no input/output file specified");
		return ERROR_INVALID_PARAMETER;
	}
	FILE* in = fopen(argv[1], "rb");
	if (!in)
	{
		fprintf(stderr, "no input file");
		return ERROR_FILE_NOT_FOUND;
	}
	// Read signature
	if (!checkSignature(in))
	{
		fprintf(stderr, "it's not a png");
		fclose(in);
		return ERROR_INVALID_DATA;
	}
	// read IHDR
	chunk* ihdr = readChunk(in);
	if (ihdr == NULL)
	{
		fclose(in);
		fprintf(stderr, "no IHDR chunk");
		return ERROR_INVALID_DATA;
	}
	if (!checkType(ihdr->type, 1) || !ihdrCheck(ihdr))
	{
		free(ihdr);
		fclose(in);
		fprintf(stderr, "no IHDR chunk");
		return ERROR_INVALID_DATA;
	}

	FILE* out = fopen(argv[2], "wb");
	if (!out)
	{
		fclose(in);
		free(ihdr);
		return ERROR_PATH_NOT_FOUND;
	}
	int c = 0;
	int h, w, d;
	printTitle(out, ihdr, &w, &h, &d);
	size_t size = 0;
	size_t arrSize = 1024;
	uchar* data = malloc(arrSize);
	free(ihdr);
	if (data == NULL)
	{
		fclose(in);
		fclose(out);
		fprintf(stderr, "out of memory");
		return ERROR_OUTOFMEMORY;
	}
	while (true)
	{
		chunk* ch = readChunk(in);
		// Invalid chunk
		if (ch == NULL)
		{
			fclose(in);
			fclose(out);
			free(data);
			fprintf(stderr, "it's not a png");
			return ERROR_INVALID_DATA;
		}
		// IDAT
		else if (checkType(ch->type, 2))
		{
			if (ch->length > 0)
			{
				size_t t = arrSize;
				while (ch->length + size >= arrSize)
				{
					arrSize *= 2;
				}
				if (arrSize != t)
				{
					uchar* datat = realloc(data, sizeof(uchar) * arrSize);
					c++;
					if (datat)
					{
						data = datat;
					}
					else
					{
						fclose(in);
						fclose(out);
						free(data);
						fprintf(stderr, "no enough memory");
						return ERROR_NOT_ENOUGH_MEMORY;
					}
				}
				memcpy(data + size, ch->data, ch->length * sizeof(uchar));
				size += ch->length;
			}
		}
		// IEND
		else if (checkType(ch->type, 0))
		{
			size_t decompressSize = h * w * d + h;
			uchar* res = malloc(decompressSize);
			if (res == NULL)
			{
				free(ch);
				fclose(in);
				fclose(out);
				free(data);
				fprintf(stderr, "no enough memory");

				return ERROR_OUTOFMEMORY;
			}

			if (size == 0 || !decompress(data, size, res, decompressSize) || ch->length != 0)
			{
				free(ch);
				free(res);
				fclose(in);
				fclose(out);
				free(data);
				fprintf(stderr, "it's not a png");
				return ERROR_INVALID_DATA;
			}
			filter(res, h, w, d, out);
			free(ch);
			if (readChunk(in) != NULL)
			{
				free(ch);
				fclose(out);
				fclose(in);
				free(res);
				free(data);
				fprintf(stderr, "it's not a png");
				return ERROR_INVALID_DATA;
			}

			fclose(out);
			fclose(in);
			free(res);
			free(data);
			return ERROR_SUCCESS;
		}
		if (!checkThatIsACorrectChunk(ch, d))
		{
			fclose(in);
			fclose(out);
			free(data);
			fprintf(stderr, "it's not a png");
			return ERROR_INVALID_DATA;
		}
		free(ch);
	}
}
