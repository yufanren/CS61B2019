public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int start;
    private int end;

    /** Create new list. */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        start = 0;
        end = 0;
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
            size -= 1;
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
            size -= 1;
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
        }
        return false;
    }

    /** Get list item and print. */
    public T get(int index) {
        if (index + start >= items.length) {
            return items[index + start - 1 - items.length];
        } else {
            return items[index + start];
        }
    }

    public void printDeque() {
        String printout = new String();
        for (int i = start; i != end; i = plusOne(i)) {
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
            return items.length - 1;
        } else {
            return i - 1;
        }
    }

    /** Resize the array into desired factor. */
    private void expand() {
        T[] temp = (T[]) new Object[items.length * 2];
        if (start > end) {
            System.arraycopy(items, start, temp, 0, items.length - start);
            System.arraycopy(items, 0, temp, items.length - start, size + start - items.length);
        } else {
            System.arraycopy(items, start, temp, 0, size);
        }
        items = temp;
        start = 0;
        end = size - 1;
    }

    private void contract() {
        T[] temp = (T[]) new Object[items.length / 2];
        if (start > end) {
            System.arraycopy(items, start, temp, 0, items.length - start);
            System.arraycopy(items, 0, temp, items.length - start, size + start - items.length);
        } else {
            System.arraycopy(items, start, temp, 0, size);
        }
        items = temp;
        start = 0;
        end = size - 1;
    }
}
