package synthesizer;

public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final means
     * the values cannot be changed at runtime. We'll discuss this and other topics
     * in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {

        int caPa = (int) Math.round(SR / frequency);
        buffer = (BoundedQueue) new ArrayRingBuffer(caPa);
        for (int i = 0; i < caPa; i += 1) {
            buffer.enqueue((double) 0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        //       Make sure that your random numbers are different from each other.
        for (int i = 0; i < buffer.capacity(); i += 1) {
            buffer.dequeue();
            buffer.enqueue(Math.random() - 0.5);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm. 
     */
    public void tic() {
        double note = buffer.dequeue();
        buffer.enqueue((note + buffer.peek()) / 2 * DECAY);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.peek();
    }

}
