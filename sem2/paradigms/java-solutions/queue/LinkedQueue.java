package queue;

public class LinkedQueue extends AbstractQueue {
    private Node head;
    private Node tail;
    private Node element;

    @Override
    protected Object elementImpl() {
        return head.value;
    }

    @Override
    protected void enqueueImpl(Object obj) {
        element = tail;
        tail = new Node(tail, obj);
        if (isEmpty()) {
            head = tail;
        } else {
            element.link = tail;
        }
    }

    @Override
    protected void dequeueImpl() {
        element = head;
        head = head.link;
    }

    @Override
    protected void clearImpl() {
        tail = null;
        head = null;
        element = null;
    }

    private static class Node {
        private final Object value;
        private Node link;

        public Node(Node node, Object obj) {
            value = obj;
            link = node;
        }
    }
}
