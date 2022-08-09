package queue;

public class ArrayQueueADT {
    private final static int INITIAL_MAX_SIZE = 32;
    private int MAX_SIZE = INITIAL_MAX_SIZE;
    private int start = 0;
    private int end = 0;
    private int size = 0;
    private Object[] arr = new Object[INITIAL_MAX_SIZE];

    //let immutable(n) =  forall i in 0..n a[i] == a'[i] where a' == after operation
    //let sizeConst = size == size' where size' = after operation
    //Model: a[i] where i in 0..size
    //Invariant: forall i in 0..size a[i] != null

    //Prev: size>=0
    //Post: immutable(size)  && size' = size + 1
    public static void enqueue(ArrayQueueADT queue, final Object element) {
        ensureCapacity(queue);
        queue.arr[queue.end] = element;
        queue.end = (++queue.end) % queue.MAX_SIZE;
        queue.size++;

    }

    //Prev: size > 0
    //Post: immutable && sizeConst && R == a[0] && R !=null
    public static Object element(ArrayQueueADT queue) {
        return queue.arr[queue.start];
    }

    //Prev: size > 0 && obj != null
    //Post:  size = size - 1  && R = a[0] && forall i in 0..size'  a'[i] == a[i+1] where size' and a' after operation
    public static Object dequeue(ArrayQueueADT queue) {
        Object elem = queue.arr[queue.start];
        queue.start = (++queue.start) % queue.MAX_SIZE;
        queue.size--;
        return elem;
    }

    //Prev: True
    //Post: R = size && sizeConst && immutable(size)
    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }

    //Prev: True
    //Post: immutable(size) && R == (size == 0) && sizeConst
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }

    //Prev: True
    //Post: 0 = size
    public static void clear(ArrayQueueADT queue) {
        queue.MAX_SIZE = INITIAL_MAX_SIZE;
        queue.start = 0;
        queue.end = 0;
        queue.size = 0;
        queue.arr = new Object[INITIAL_MAX_SIZE];
    }


    //Prev True
    //Post immutable && sizeConst
    private static void ensureCapacity(ArrayQueueADT queue) {
        if (queue.size == queue.MAX_SIZE - 1) {
            queue.MAX_SIZE *= 2;
            Object[] tmp = new Object[queue.MAX_SIZE];
            if (queue.end < queue.start) {
                System.arraycopy(queue.arr, queue.start, tmp, 0, queue.arr.length - queue.start);
                System.arraycopy(queue.arr, 0, tmp, queue.arr.length - queue.start, queue.end);
            } else {
                System.arraycopy(queue.arr, queue.start, tmp, 0, queue.end - queue.start);
            }
            queue.arr = tmp;
            queue.end = queue.size;
            queue.start = 0;
        }
    }

    //Prev: size > 0 && obj != null
    //Post: immutable(size) && sizeConst && R = all i in 0..size where is a[i]==obj && R >= 0
    public static int count(ArrayQueueADT queue, Object obj) {
        if (queue.end < queue.start) {
            return searchInRange(0, queue.end, queue.arr, obj) + searchInRange(queue.start, queue.arr.length, queue.arr, obj);
        } else {
            return searchInRange(queue.start, queue.end, queue.arr, obj);
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
