import math


def cnk(n, k):
    return math.floor(factorial(n) / (factorial(n - k) * factorial(k)))


def factorial(n):
    ret = 1
    for i in range(1, n+1):
        ret *= i
    return ret


n, k = map(int, list(input().split()))
chooses = []
for i in range(n):
    tmp = [0 for l in range(k)]
    tmp[0]=1
    for j in range(1,k):
        tmp[j]=(cnk(i, j))
    chooses.append(tmp)
counter = 0
arr = list(map(int, input().split()))
arr.insert(0,0)
for i in range(1,k+1):
    for j in range(arr[i - 1] + 1, arr[i]):
        counter += chooses[n - j][k - i]
print(counter)
