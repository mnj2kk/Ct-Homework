concat([], B, B).
concat([H | T], B, [H | R]) :- concat(T, B, R).
foldLeft([], Z, _, Z).
foldLeft([H | T], Z, F, R) :- G =.. [F, Z, H, RH], call(G), foldLeft(T, RH, F, R).
prime(N) :-
	\+ composite_table(N),!.
inc(N, R) :- number(R), !, N is R - 1.
inc(N, R) :- R is N + 1,!.
nextPrime(S,R):-  inc(S,T),prime(T),(R is T),!.
nextPrime(S,R):-  inc(S,T),composite(T),nextPrime(T,RR),R is RR,!.
composite(N) :-
	composite_table(N).

init(MAX_N) :-
	sieve(2,MAX_N).
sieve(S,N) :-
	S =< sqrt(N),
	P is 2*S,
 asserter(P,S,N),
	nextPrime(S,S1),
	sieve(S1,N).
asserter(S,I,N) :-
	 S =< N,
	(assert(composite_table(S)),
	S1 is S+I,
	asserter(S1,I,N)),!.
asserter(S,I,N).
get_divisors(1,_,V,V).
get_divisors(N,_,V,	V1):-prime(N),concat(V,[N],V1),!.
get_divisors(N,S,V,V):- S > sqrt(N),!.
get_divisors(N,S,V,R):- 0 is mod(N,S),N1 is N/S,concat(V,[S],V1),get_divisors(N1,S,V1,T),R = T,!.
get_divisors(N,S,V,R):- nextPrime(S,S1),get_divisors(N,S1,V,T),R = T,!.
prime_divisors(N,V):- number(N),is_list(V),get_divisors(N,2,[],R),V == R,!.
prime_divisors(N,V):- number(N),get_divisors(N,2,[],R),V = R,!.
mult(A, B, R) :- R is A * B.
prime_divisors(N,V):- foldLeft(V,1,mult,N1),get_divisors(N1,2,[],R),V == R,N is N1,!.
nth_prime(N, P):- number(N),find_nth_prime(N, 2,T),P is T,!.
find_nth_prime(N,S, P):- N > 1,nextPrime(S,S1),inc(N1,N),find_nth_prime(N1,S1,R),P is R,!.
find_nth_prime(N,S,S).
nth_prime(N, D):- number_nth_prime(1, 2,D,N1),N is N1,!.
number_nth_prime(N,S,D, P):- S < D,nextPrime(S,S1),inc(N,N1),number_nth_prime(N1,S1,D,R),P is R,!.
number_nth_prime(N,_,_,N).
lcm(A,B,C):- prime_divisors(A,V1),prime_divisors(B,V2),lcm_rec(V1,V2,R),C is R.
lcm_rec([],V,C):-foldLeft(V,1,mult,N),C is N,!.
lcm_rec(V,[],C):-foldLeft(V,1,mult,N),C is N,!.
lcm_rec([H1 | V1],[H2 |V2],C):- H1<H2,lcm_rec(V1,[H2|V2],T), C is T * H1, !.
lcm_rec([H1 | V1],[H2 |V2],C):- H1>H2,lcm_rec([H1|V1],V2,T), C is T * H2, !.
lcm_rec([H1 | V1],[H2 |V2],C):- lcm_rec(V1,V2,T), C is T * H1 ,!.