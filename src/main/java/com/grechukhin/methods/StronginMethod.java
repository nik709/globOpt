package com.grechukhin.methods;

public class StronginMethod extends BaseMethod {

    private int mSource;
    private double mR;

    public StronginMethod(double a, double b
            , double epsilon
            , int source, double r) {
        super(a, b, epsilon);
        mSource = source;
        mR = (r > 1 ? r : 1.5);
    }

    @Override
    public BaseMethod call() {
        clear();
        makeInitTrial();
        for (int i = 2; i < mSource; ++i) {
            if (!isTrialSorted()) {
                sortTrials();
            }
            int t = recalculateCharacteristic();
            double newX;
            if (Math.abs(getTrials().get(t).getX() - getTrials().get(t - 1).getX()) <= getEpsilon()) {
                return this;
            }
            double M = M();
            newX = .5 * (getTrials().get(t).getX() + getTrials().get(t - 1).getX())
                    - (getTrials().get(t).getY() - getTrials().get(t - 1).getY()) / (2 * (M > 0 ? mR*M : 1));
            makeTrial(newX);
        }
        return this;
    }

    @Override
    protected int recalculateCharacteristic() {
        double M = M();
        getCharacteristics().clear();
        int t = 1;
        double maxR = Double.MIN_VALUE;
        for (int i = 1; i < getTrials().size(); ++i) {
            double deltaX = getTrials().get(i).getX() - getTrials().get(i - 1).getX();
            double deltaZ = getTrials().get(i).getY() - getTrials().get(i - 1).getY();
            double m = M > 0 ? mR*M : 1;
            double characteristic =
                    m * deltaX - deltaZ * deltaZ / m * deltaX
                            - 2 * (getTrials().get(i).getY() + getTrials().get(i - 1).getY());
            if (characteristic > maxR) {
                t = i;
                maxR = characteristic;
            }
            getCharacteristics().add(characteristic);
        }
        return t;
    }

    private void makeInitTrial() {
        makeTrial(mA);
        makeTrial(mB);
        makeTrial(.5 * (mB + mA));
    }
}
