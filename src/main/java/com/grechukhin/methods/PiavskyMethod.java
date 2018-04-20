package com.grechukhin.methods;

import java.util.HashMap;
import java.util.Map;

public class PiavskyMethod extends BaseMethod {

    private int mSource;
    private double mR;

    public PiavskyMethod(double a, double b
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
            newX = newX(t);
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
            double characteristic =
                    (M > 0 ? mR*M : 1) * (getTrials().get(i).getX() - getTrials().get(i - 1).getX())
                    - (getTrials().get(i).getY() + getTrials().get(i - 1).getY())/2;
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

    private double newX(int t) {
        double M = M();
        return .5 * (getTrials().get(t).getX() + getTrials().get(t - 1).getX())
                - (getTrials().get(t).getY() - getTrials().get(t - 1).getY()) / (2 * (M > 0 ? mR*M : 1));
    }

    @Override
    public Map<String, Object> getParams() {
        return new HashMap<String, Object>() {{
            put("a", mA);
            put("b", mB);
            put("source", mSource);
            put("epsilon", getEpsilon());
            put("R", mR);
        }};
    }
}
