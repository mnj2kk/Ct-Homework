package queue;

public class ArrayQueueModule {
    private final static int INITIAL_MAX_SIZE = 32;
    private static int MAX_SIZE = INITIAL_MAX_SIZE;
    private static int start = 0;
    private static int end = 0;
    private static int size = 0;
    private static Object[] arr = new Object[INITIAL_MAX_SIZE];

    //let immutable(n) =  forall i in 0..n a[i] == a'[i] where a' == after operation
    //let sizeConst = size == size' where size' = after operation
    //Model: a[i] where i in 0..size
    //Invariant: forall i in 0..size a[i] != null

    //Prev: el != null
    //Post: immutable(size)  && size' = size + 1 && a[size'] = el
    public static void enqueue(final Object element) {
        ensureCapacity();
        arr[end] = element;
        end = (++end) % MAX_SIZE;
        size++;

    }

    //Prev: size > 0
    //Post: immutable && sizeConst && R == a[0] && R !=null
    public static Object element() {
        return arr[start];
    }

    //Prev: size > 0
    //Post: size = size - 1  && R = a[0] && forall i in 0..size'  a'[i] == a[i+1] where size' and a' after operation
    public static Object dequeue() {
        Object elem = arr[start];
        start = (++start) % MAX_SIZE;
        size--;
        return elem;
    }

    //Prev: True
    //Post: R = size && sizeConst && immutable(size)
    public static int size() {
        return size;
    }

    //Prev: True
    //Post: immutable(size) && R == (size == 0) && sizeConst
    public static boolean isEmpty() {
        return size == 0;
    }

    //Prev: True
    //Post: size = 0
    public static void clear() {
        MAX_SIZE = INITIAL_MAX_SIZE;
        start = 0;
        end = 0;
        size = 0;
        arr = new Object[INITIAL_MAX_SIZE];
    }


    //Prev True
    //Post immutable(size) && sizeConst
    private static void ensureCapacity() {
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
    //Post: immutable(size) && sizeConst && R = |{ i: 0..size a[i]==obj}| && R >= 0
    public static int count(Object obj) {
        if (end < start) {
            return searchInRange(0, end, arr, obj) + searchInRange(start, arr.length, arr, obj);
        } else {
            return searchInRange(start, end, arr, obj);
        }
    }

    //Prev:  0 <= start < end < arr.length
    //Post:  R = all i in start..end where is arr[i]==obj && R >= 0
    private static int searchInRange(int start, int end, Object[] arr, Object object) {
        int count = 0;
        for (int i = start; i < end; i++) {
            if (arr[i].equals(object)) {
                count++;
            }
        }
        return count;
    }


}
