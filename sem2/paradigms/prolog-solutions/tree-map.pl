merge(nil,nil,nil).
merge(Tree,nil,Tree).
merge(nil,Tree,Tree).
merge(tree(PRIORITY1,KEY1,VALUE1,LEFT1,RIGHT1),tree(PRIORITY2,KEY2,VALUE2,LEFT2,RIGHT2),tree(PRIORITY1,KEY1,VALUE1,LEFT1,RIGHTR)):-
    PRIORITY1 > PRIORITY2,
    merge(RIGHT1,tree(PRIORITY2,KEY2,VALUE2,LEFT2,RIGHT2),RIGHTR),!.
merge(tree(PRIORITY1,KEY1,VALUE1,LEFT1,RIGHT1),tree(PRIORITY2,KEY2,VALUE2,LEFT2,RIGHT2),tree(PRIORITY2,KEY2,VALUE2,LEFTR,RIGHT2)):-
    PRIORITY1 < PRIORITY2,
    merge(tree(PRIORITY1,KEY1,VALUE1,LEFT1,RIGHT1),LEFT2,LEFTR),!.

split(nil,_,nil,nil).
split(tree(PRIORITY1,KEY1,VALUE1,LEFT1,RIGHT1),K,tree(PRIORITY1,KEY1,VALUE1,LEFT1,T1),T2):-
    K > KEY1,split(RIGHT1,K,T1,T2),!.
split(tree(PRIORITY1,KEY1,VALUE1,LEFT1,RIGHT1),K,T1,tree(PRIORITY1,KEY1,VALUE1,T2,RIGHT1)):-
    split(LEFT1,K,T1,T2),!.

map_remove(T,KEY,R) :-
    KEYM is KEY+ 1,split(T,KEY,T1,T23),
    split(T,KEYM,T2,T3),merge(T1,T3,R),!.


map_put(T,KEY,VAL,R):-map_remove(T,KEY,D),
    split(D,KEY,T1,T2),rand_int(1000000,RAND),
    merge(T1,tree(RAND,KEY,VAL,nil,nil),R1),merge(R1,T2,R),!.



build([],Tree,Tree).
build([(KEY,VAL)|T],nil,R):-
    rand_int(1000000,RAND),build(T,tree(RAND,KEY,VAL,nil,nil),R),!.
build([(KEY,VAL)|T],tree(PRIORITY1,KEY1,VALUE1,LEFT1,RIGHT1),R):-
    map_put(tree(PRIORITY1,KEY1,VALUE1,LEFT1,RIGHT1),KEY,VAL,P),build(T,P,R),!.
map_build(T,R):- build(T,nil,R).
map_get(tree(PRIORITY1,KEY1,VALUE1,LEFT1,RIGHT1),K,V) :-
    K = KEY1 ,V=VALUE1,!.
map_get(tree(PRIORITY1,KEY1,VALUE1,LEFT1,RIGHT1),K,V) :-
    K < KEY1 ,map_get(LEFT1,K,V),!.
map_get(tree(PRIORITY1,KEY1,VALUE1,LEFT1,RIGHT1),K,V) :-
    K > KEY1 ,map_get(RIGHT1,K,V),!.
map_biggest(tree(_,KEY,_,_,nil),V):- V is KEY,!.
map_biggest(tree(_,KEY,_,_,RIGHT),R):-map_biggest(RIGHT,R),!.
map_floorKey(T,K,V):- K1 is K+1,split(T,K1,D1,D2	),map_biggest(D1,V).

map_replace(T,K,V,R):- map_get(T,K,P),map_put(T,K,V,R),!.
map_replace(T,K,V,T).