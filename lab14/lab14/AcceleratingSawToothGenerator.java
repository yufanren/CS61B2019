package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int state;
    private int period;
    private double factor;
    private double factorMultiplier;

    public AcceleratingSawToothGenerator(int period, double factor) {
        state = 0;
        this.period = period;
        this.factor = factor;
    }

    public double next() {
        state++;
        state %= period;
        if (state == 0) {
            period *= factor;
        }
        return 2 * ((double) state / period) - 1;
    }
}
