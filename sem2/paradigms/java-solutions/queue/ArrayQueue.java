package queue;

public class ArrayQueue extends AbstractQueue {
    private final static int INITIAL_MAX_SIZE = 32;
    private int MAX_SIZE = INITIAL_MAX_SIZE;
    private int start = 0;
    private int end = 0;

    //let immutable(n) =  forall i in 0..n a[i] == a'[i] where a' == after operation
    //let sizeConst = size == size' where size' = after operation
    //Model: a[i] where i in 0..size
    //Invariant: forall i in 0..size a[i] != null
    private Object[] arr = new Object[INITIAL_MAX_SIZE];

    //Prev: size>=0
    //Post: immutable(size)
    @Override
    protected void enqueueImpl(final Object element) {
        ensureCapacity();
        arr[end] = element;
        end = (++end) % MAX_SIZE;

    }

    //Prev: size > 0 && obj != null
    //Post:   R = a[0] && forall i in 0..(size-1)  a'[i] == a[i+1] where a' after operation
    @Override
    protected void dequeueImpl() {
        start = (++start) % MAX_SIZE;
    }


    //Prev: size > 0
    //Post: immutable && sizeConst && R == a[0]
    @Override
    protected Object elementImpl() {
        return arr[start];
    }

    //Prev: True
    //Post: True
    @Override
    protected void clearImpl() {
        MAX_SIZE = INITIAL_MAX_SIZE;
        start = 0;
        end = 0;
        arr = new Object[INITIAL_MAX_SIZE];
    }

    //Prev True
    //Post immutable && sizeConst
    private void ensureCapacity() {
        if (size == MAX_SIZE - 1) {
            MAX_SIZE *= 2;
            Object[] tmp = new Object[MAX_SIZE];
            if (end < start) {
                System.arraycopy(arr, start, tmp, 0, arr.length - start);
                System.arraycopy(arr, 0, tmp, arr.length - start, end);
            } else {
                System.arraycopy(arr, start, tmp, 0, end - start);
            }
            arr = tmp;
            end = size;
            start = 0;
        }
    }

    //Prev: size > 0 && obj != null
    //Post: immutable(size) && sizeConst && R = all i in 0..size where is a[i]==obj && R >= 0
    public int count(Object obj) {
        if (end < start) {
            return searchInRange(0, end, arr, obj) + searchInRange(start, arr.length, arr, obj);
        } else {
            return searchInRange(start, end, arr, obj);
        }
    }

    //Prev:  0 <= start < end < arr.length
    //Post:  R = all i in start..end where is arr[i]==obj && R >= 0
    private int searchInRange(int start, int end, Object[] arr, Object object) {
        int count = 0;
        for (int i = start; i < end; i++) {
            if (arr[i].equals(object)) {
                count++;
            }
        }
        return count;
    }

}
