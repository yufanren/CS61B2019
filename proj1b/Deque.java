public interface Deque<T> {

    void addFirst(T input);

    void addLast(T input);

    T removeFirst();

    T removeLast();

    int size();

    T get(int index);

    void printDeque();

}

