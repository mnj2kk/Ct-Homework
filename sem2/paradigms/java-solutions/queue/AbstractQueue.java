package queue;

import java.util.function.Predicate;

abstract public class AbstractQueue implements Queue {
    protected int size = 0;

    @Override
    public Object element() {
        return elementImpl();
    }

    protected abstract Object elementImpl();

    @Override
    public void enqueue(Object obj) {
        enqueueImpl(obj);
        size++;
    }

    protected abstract void enqueueImpl(Object obj);

    @Override
    public Object dequeue() {
        Object res = element();
        size--;
        dequeueImpl();
        return res;
    }

    protected abstract void dequeueImpl();

    //Prev: True
    //Post: R = size && sizeConst && immutable(size)
    @Override
    public final int size() {
        return size;
    }

    //Prev: True
    //Post: immutable(size) && R == (size == 0) && sizeConst
    @Override
    public final boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        size = 0;
        clearImpl();
    }

    protected abstract void clearImpl();

    @Override
    public int countIf(Predicate<Object> predicate) {
        int counter = 0;
        for (int i = 0; i < size; i++) {
            Object elem = dequeue();
            if (predicate.test(elem)) {
                counter++;
            }
            enqueue(elem);
        }
        return counter;
    }


}
