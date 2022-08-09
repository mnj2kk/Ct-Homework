package queue;


public class Tester {
    private static final ArrayQueueADT adt = new ArrayQueueADT();
    private static final ArrayQueue queue = new ArrayQueue();
    private static final AbstractQueue linkedQueue = new LinkedQueue();

    public static void main(String[] args) {
        boolean assertOn = false;
        assert assertOn = true;
        //Code from docs,I ¯\_(ツ)_/¯  why Idea don't like it
        if (!assertOn) {
            System.out.println("Assertions aren't enabled");
            return;
        }
        for (int j = 0; j < 10; j++) {

            for (int i = 1; i < 100; i++) {
                testEnqueue(i, 1, i);
            }
            for (int i = 1; i < 50; i++) {
                testDequeue(i, 99 - i);
            }

            for (int i = 50; i < 150; i++) {
                testEnqueue(i, 50, i + 1);
            }
            for (int i = 1; i < 50; i++) {
                testDequeue(49 + i, 150 - i);
            }
            clear();
        }
        System.out.println("Successfully passed all tests");
    }

    private static void clear() {
        ArrayQueueModule.clear();
        ArrayQueueADT.clear(adt);
        queue.clear();
        linkedQueue.clear();
        testIsEmpty(true);
    }

    private static void testIsEmpty(boolean expected) {
        isValid(expected, ArrayQueueModule.isEmpty(), ArrayQueueADT.isEmpty(adt), linkedQueue.isEmpty(), queue.isEmpty());
    }

    private static void testSizes(int expected) {
        isValid(expected, ArrayQueueModule.size(), ArrayQueueADT.size(adt), linkedQueue.size(), queue.size());
    }

    private static void testCount(int expected, Object obj) {
        isValid(expected, ArrayQueueModule.count(obj), ArrayQueueADT.count(adt, obj), queue.count(obj), expected);
    }

    private static void testElement(Object expected) {
        isValid(expected, ArrayQueueModule.element(), ArrayQueueADT.element(adt), linkedQueue.element(), queue.element());
    }

    private static void testDequeue(Object expected, int expectedSize) {
        isValid(expected, ArrayQueueModule.dequeue(), ArrayQueueADT.dequeue(adt), linkedQueue.dequeue(), queue.dequeue());
        testSizes(expectedSize);
        testIsEmpty(false);
    }

    private static void testEnqueue(Object i, Object expectedEl, int expectedSize) {
        ArrayQueueModule.enqueue(i);
        ArrayQueueADT.enqueue(adt, i);
        linkedQueue.enqueue(i);
        queue.enqueue(i);
        testElement(expectedEl);
        testSizes(expectedSize);
        testCount(1, i);
        testIsEmpty(false);

    }

    private static void isValid(Object expected, Object first, Object second, Object third, Object fourth) {
        assert first != null;
        assert second != null;
        assert third != null;
        assert fourth != null;
        assert first.equals(expected) && second.equals(expected) && third.equals(expected) && fourth.equals(expected);
    }

}
