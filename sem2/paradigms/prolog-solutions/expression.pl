:- load_library('alice.tuprolog.lib.DCGLibrary').
nonvar(V, _) :- var(V).
nonvar(V, T) :- nonvar(V), call(T).
lookup(K, [(K, V) | _], V).
lookup(K, [_ | T], V) :- lookup(K, T, V).
filter([], _, []).
filter([H | T], P, [H | RT]) :- G =.. [P, H], call(G), !, filter(T, P, RT).
filter([H | T], P, RT) :- filter(T, P, RT).

variable(Name, variable(Name)).
const(Value, const(Value)).

op_add(A, B, operation(op_add, A, B)).
op_sub(A, B, operation(op_sub, A, B)).
op_mul(A, B, operation(op_mul, A, B)).
op_dvd(A, B, operation(op_dvd, A, B)).

operation(op_add, A, B, R) :- R is A + B.
operation(op_sub, A, B, R) :- R is A - B.
operation(op_mul, A, B, R) :- R is A * B.
operation(op_dvd, A, B, R) :- R is A / B.

evaluate(const(Value), _, Value).
evaluate(variable(Name), Vars, R) :- lookup(Name, Vars, R).
evaluate(operation(Op, A, B), Vars, R) :-
    evaluate(A, Vars, AV),
    evaluate(B, Vars, BV),
    operation(Op, AV, BV, R).


digits_p([]) --> [].
digits_p([H | T]) -->
  { member(H, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9','.'])},
  [H],
  digits_p(T).
expr_p(variable(Name)) -->[Name],{ member(Name, [x, y, z]) }.
expr_p(const(Value)) -->
   { nonvar(Value, number_chars(Value, Chars)) },
   {filter(Chars,notSpace,S)},
   digits_p(S),
   { S = [_ | _], number_chars(Value, S) }.
op_p(op_add) --> ['+'].
op_p(op_sub) --> ['-'].
op_p(op_mul) --> ['*'].
op_p(op_div) --> ['/'].
notSpace(I) :- \+ member(I,[' ']).
expr_p(operation(Op, A, B)) -->  ['('], expr_p(A), [' '], expr_p(B),[' '],op_p(Op), [')'].
suffix_str(E, A) :- ground(E), phrase(expr_p(E), C), atom_chars(A, C).
suffix_str(E, A) :-   atom(A), atom_chars(A, C), phrase(expr_p(E), C).