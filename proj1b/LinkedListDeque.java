public class LinkedListDeque<T> implements Deque<T> {

    private Node sentinal;
    private int size;

    /** Object initiation. */
    public LinkedListDeque() {
        sentinal = new Node(null, null, null);
        sentinal.prev = sentinal;
        sentinal.next = sentinal;
        size = 0;
    }

    /** public LinkedListDeque(T input) {
        sentinal = new Node(null, null, null);
        sentinal.next = new Node(sentinal, input, sentinal);
        sentinal.prev = sentinal.next;
        size = 1;
    } */

    /** Add to the linked list. */
    @Override
    public void addFirst(T input) {
        Node second = sentinal.next;
        sentinal.next = new Node(sentinal, input, sentinal.next);
        second.prev = sentinal.next;
        size += 1;
    }
    @Override
    public void addLast(T input) {
        Node last = sentinal.prev;
        sentinal.prev = new Node(sentinal.prev, input, sentinal);
        last.next = sentinal.prev;
        size += 1;
    }

    /** Remove from the linked list. */
    @Override
    public T removeFirst() {
        if (sentinal.next.equals(sentinal)) {
            return null;
        }
        size -= 1;
        Node temp = sentinal.next;
        sentinal.next.next.prev = sentinal;
        sentinal.next = temp.next;
        return temp.data;
    }
    @Override
    public T removeLast() {
        if (sentinal.prev.equals(sentinal)) {
            return null;
        }
        size -= 1;
        Node temp = sentinal.prev;
        sentinal.prev.prev.next = sentinal;
        sentinal.prev = temp.prev;
        return temp.data;
    }


    /**Return size of list. */
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /** Get list items and print. */
    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        Node temp = sentinal.next;
        for (int i = index; i > 0; i -= 1) {
            temp = temp.next;
        }
        return temp.data;
    }

    public T getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        } else {
            return sentinal.next.getRecur(index);
        }

    }
    @Override
    public void printDeque() {
        Node temp = sentinal;
        String printout = new String();
        while (!(temp.next).equals(sentinal)) {
            temp = temp.next;
            printout += String.valueOf(temp.data);
            printout += " ";
        }
        System.out.println(printout);
    }

    /** Hidden naked recursive structure. */
    private class Node {
        private Node prev;
        private Node next;
        private T data;

        private Node(Node a, T input, Node b) {
            prev = a;
            data = input;
            next = b;
        }

        private T getRecur(int x) {
            if (x == 0) {
                return data;
            } else {
                return next.getRecur(x - 1);
            }
        }
    }

}




