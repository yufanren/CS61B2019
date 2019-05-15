package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    private int state;
    private int period;

    public SawToothGenerator(int period) {
        state = 0;
        this.period = period;
    }

    public double next() {
        state++;
        state %= period;
        return 2 * ((double) state / period) - 1;
    }
}
