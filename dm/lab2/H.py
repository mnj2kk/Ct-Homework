from fractions import Fraction

n = int(input())
alphabet = 'abcdefghijklmnopqrstuvwxyz'
pr = [int(x) for x in input().split()]
length = sum(pr)
startOfSymbols = []
endOfSymbols = []
i = 0
sorted(pr)
for v in pr:
    startOfSymbols.append(Fraction(i, length))
    i += v
    endOfSymbols.append(Fraction(i, length))
codeBin = input()
code = Fraction(int(codeBin, 2), 2 ** len(codeBin))
line=""
for j in range(length):
    for i in range(len(pr)):
        if startOfSymbols[i] <= code < endOfSymbols[i]:
            line+=alphabet[i]
            break
    code = Fraction((code - startOfSymbols[i]) , (endOfSymbols[i] - startOfSymbols[i]))
print(line)