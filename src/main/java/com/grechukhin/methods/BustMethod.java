package com.grechukhin.methods;

import java.util.List;

public final class BustMethod extends BaseMethod {

    private int mSource;

    public BustMethod(double a, double b, int source, double epsilon) {
        super(a, b, epsilon);
        mSource = source;
    }

    @Override
    public BaseMethod call() {
        clear();
        makeInitTrial();
        for (int i = 2; i < mSource - 1; ++i) {
            if (!isTrialSorted()) {
                sortTrials();
            }
            int t = recalculateCharacteristic();
            double newX;
            if (Math.abs(getTrials().get(t).getX() - getTrials().get(t - 1).getX()) <= getEpsilon()) {
                return this;
            }
            newX = .5 * (getTrials().get(t).getX() + getTrials().get(t - 1).getX());
            makeTrial(newX);
        }
        return this;
    }

    private void makeInitTrial() {
        makeTrial(mA);
        makeTrial(mB);
        makeTrial(.5 * (mB + mA));
    }

    @Override
    protected int recalculateCharacteristic() {
        getCharacteristics().clear();
        List<Double> characteristics = getCharacteristics();
        int t = 1;
        double maxR = Double.MIN_VALUE;
        for (int i = 1; i < getTrials().size(); ++i) {
            double characteristic = getTrials().get(i).getX() - getTrials().get(i - 1).getX();
            if (characteristic > maxR) {
                t = i;
                maxR = characteristic;
            }
            characteristics.add(characteristic);
        }
        return t;
    }
}
