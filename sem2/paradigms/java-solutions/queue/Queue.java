package queue;

import java.util.function.Predicate;

public interface Queue {
    //let immutable(n) =  forall i in 0..n a[i] == a'[i] where a' == after operation
    //let sizeConst = size == size' where size' = after operation
    //Model: a[i] where i in 0..size
    //Invariant: forall i in 0..size a[i] != null

    //Prev: size > 0
    //Post: immutable(size) && sizeConst && R == a[0] && R !=null
    Object element();

    //Prev: obj != null
    //Post: immutable(size)  && size' = size + 1 && a[size]==obj
    void enqueue(Object obj);

    //Prev: size > 0
    //Post:  size = size - 1  && R = a[0] && forall i in 0..size'  a'[i] == a[i+1] where size' and a' after operation
    Object dequeue();

    //Prev: True
    //Post: R = size &&  immutable(size) && sizeConst
    int size();

    //Prev: True
    //Post: immutable(size) && R == (size == 0) && sizeConst
    boolean isEmpty();

    //Prev: True
    //Post: size=0;
    void clear();


    //Prev: True
    //Post: R= all  a[i]== predicate where i in 0..size && immutable(size) && sizeConst
    int countIf(Predicate<Object> predicate);

}
