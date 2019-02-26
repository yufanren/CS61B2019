package synthesizer;

import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        first = 0;
        last = 0;
        rb = (T[]) new Object[capacity];
        this.fillCount = 0;
        this.capacity = capacity;
    }

    private int plusOne(int i) {
        if (i == capacity - 1) {
            return 0;
        }
        return i + 1;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        fillCount += 1;
        last = plusOne(last);
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T temp = rb[first];
        rb[first] = null;
        fillCount -= 1;
        first = plusOne(first);
        return temp;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    public Iterator<T> iterator() {

        return new RingBufferIterator();
    }

    private class RingBufferIterator implements Iterator<T> {
        private int wizPos;

        RingBufferIterator() {
            wizPos = 0;
        }

        public boolean hasNext() {
            return wizPos < capacity;
        }

        public T next() {
            T returnItem = rb[wizPos];
            wizPos += 1;
            return returnItem;
        }
    }
}
