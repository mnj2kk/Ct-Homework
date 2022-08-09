#include <utility>
template< typename T, bool descending >
size_t partition(T* arr, size_t s, size_t e)
{
	T m = arr[(e + s) / 2];
	while (true)
	{
		if (descending)
		{
			while (arr[s] > m)
			{
				s++;
			}
			while (arr[e] < m)
			{
				e--;
			}
		}
		else
		{
			while (arr[s] < m)
			{
				s++;
			}
			while (arr[e] > m)
			{
				e--;
			}
		}
		if (s >= e)
		{
			return e;
		}
		std::swap(arr[s++], arr[e--]);
	}
}
template< typename T, bool descending >
void quicksort(T* arr, size_t n, size_t s, size_t e)
{
	while (s < e)
	{
		size_t m = partition< T, descending >(arr, s, e);
		if (e - m > m - s)
		{
			quicksort< T, descending >(arr, n, s, m);
			s = m + 1;
		}
		else
		{
			quicksort< T, descending >(arr, n, m, e);
			e = m;
		}
	}
}