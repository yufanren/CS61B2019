public class LinkedListDeque<T> {

    private node sentinal;
    private int size;

    /** Object initiation. */
    public LinkedListDeque() {
        sentinal = new node(null, null, null);
        sentinal.prev = sentinal;
        sentinal.next = sentinal;
        size = 0;
    }

    public LinkedListDeque(T input) {
        sentinal = new node(null, null, null);
        sentinal.next = new node(sentinal, input, sentinal);
        sentinal.prev = sentinal.next;
        size = 1;
    }

    /** Add to the linked list. */
    public void addFirst(T input) {
        node second = sentinal.next;
        sentinal.next = new node(sentinal, input, sentinal.next);
        second.prev = sentinal.next;
        size += 1;
    }

    public void addLast(T input) {
        node last = sentinal.prev;
        sentinal.prev = new node(sentinal.prev, input, sentinal);
        last.next = sentinal.prev;
        size += 1;
    }

    /** Remove from the linked list. */
    public T removeFirst() {
        if (sentinal.next.equals(sentinal)) {
            return null;
        }
        size -= 1;
        node temp = sentinal.next;
        sentinal.next.next.prev = sentinal;
        sentinal.next = temp.next;
        return temp.data;
    }

    public T removeLast() {
        if (sentinal.prev.equals(sentinal)) {
            return null;
        }
        size -= 1;
        node temp = sentinal.prev;
        sentinal.prev.prev.next = sentinal;
        sentinal.prev = temp.prev;
        return temp.data;
    }


    /**Return size of list. */
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    /** Get list items and print. */
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        node temp = sentinal.next;
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

    public void printDeque() {
        node temp = sentinal;
        String printout = new String();
        while (!(temp.next).equals(sentinal)) {
            temp = temp.next;
            printout += String.valueOf(temp.data);
            printout += " ";
        }
        System.out.println(printout);
    }

    /** Hidden naked recursive structure. */
    private class node {
        private node prev;
        private node next;
        private T data;

        private node(node a, T input, node b) {
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