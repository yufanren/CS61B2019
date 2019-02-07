public class ArrayDeque<T> {
    T[] items;
    int size;
    int start;
    int end;

    /** Create new list. */
    public ArrayDeque() {
        T[] items = (T[]) new Object[8];
        size = 0;
    }

    public ArrayDeque(T[] input) {
        T[] items = (T[]) new Object[input.length * 2];
        size = input.length;
        System.arraycopy(input, 0, items, 0, input.length);
        start = 0;
        end = input.length - 1;
    }

    /** Add to array. */
    public void addFirst(T input) {
        if (size == items.length) {
            expand();
        }
        if (size == 0) {
            start = 0;
            end = 0;
        } else {
            start = minusOne(start);
        }
        items[start] = input;
        size += 1;
    }

    public void addLast(T input) {
        if (size == items.length) {
            expand();
        }
        if (size == 0) {
            start = 0;
            end = 0;
        } else {
            end = plusOne(end);
        }
        items[end] = input;
        size += 1;
    }

    /** Remove from array. */
    public T removeFirst() {
        if (size <= items.length / 4) {
            contract();
        }
        if (size == 0) {
            return null;
        } else {
            T temp = items[start];
            items[start] = null;
            start = plusOne(start);
            return temp;
        }

    }

    public T removeLast() {
        if (size <= items.length / 4) {
            contract();
        }
        if (size == 0) {
            return null;
        } else {
            T temp = items[end];
            items[end] = null;
            end = minusOne(end);
            return temp;
        }
    }

    /** Return size of list. */
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

    /** Get list item and print. */
    public T get(int index) {
        if (index + size > items.length) {
            return items[index + size - 1 - items.length];
        } else {
            return items[index + size];
        }
    }

    public void printDeque() {
        String printout = new String();
        for (int i = start; i != plusOne(end); i = plusOne(i)) {
            printout += String.valueOf(items[i]);
            printout += " ";
        }
        printout += String.valueOf(items[end]);
        System.out.println(printout);
    }

    /** Math. */
    private int plusOne(int i) {
        if (i == items.length - 1) {
            return 0;
        } else {
            return i + 1;
        }
    }

    private int minusOne(int i) {
        if (i == 0) {
            return items.length -1;
        } else {
            return i - 1;
        }
    }

    /** Resize the array into desired factor. */
    private void expand() {
        T[] temp = (T[]) new Object[items.length * 2];
        System.arraycopy(items, start, temp, 0, size);
        items = temp;
        start = 0;
        end = size - 1;
    }

    private void contract() {
        T[] temp = (T[]) new Object[items.length / 2];
        System.arraycopy(items, start, temp, 0, size);
        items = temp;
        start = 0;
        end = size - 1;
    }













}