package com.grechukhin.methods;

import com.grechukhin.models.FunctionInterface;
import com.grechukhin.models.ModelFactory;
import com.grechukhin.models.TrialInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class BaseMethod {

    private final double mEpsilon;

    private FunctionInterface mFunction;
    private final List<TrialInterface> mTrials = new ArrayList<>();
    private final List<Double> mCharacteristics = new ArrayList<>();

    protected double mA;
    protected double mB;

    public BaseMethod(double a, double b, double epsilon) {
        mA = a;
        mB = b;
        mEpsilon = epsilon;
    }

    public BaseMethod setFunction(FunctionInterface function) {
        mFunction = function;
        return this;
    }

    public List<TrialInterface> getTrials() {
        return new ArrayList<>(mTrials);
    }

    protected void makeTrial(double x) {
        if (x < mA || x > mB) {
            return;
        }

        TrialInterface trial = (TrialInterface) ModelFactory.getInstance().createModel(TrialInterface.class);
        if (trial != null) {
            trial.setData(x, mFunction.calculate(x));
            mTrials.add(trial);
        }
    }

    protected double getFunctionValue(int i) {
        return getTrials().get(i).getY();
    }

    protected void sortTrials() {
        Collections.sort(mTrials, new Comparator<TrialInterface>() {
            @Override
            public int compare(TrialInterface t1, TrialInterface t2) {
                return Double.compare(t1.getX(), t2.getX());
            }
        });
    }

    protected boolean isTrialSorted() {
        if (getTrials().isEmpty()) {
            return true;
        }

        TrialInterface prevTrial = getTrials().get(0);
        for (TrialInterface trial : getTrials()) {
            if (prevTrial.getX() > trial.getX()) {
                return false;
            }
            prevTrial = trial;
        }

        return true;
    }

    protected List<Double> getCharacteristics() {
        return mCharacteristics;
    }

    protected int getMaxCharacteristicPosition() {
        int maxR = 0;
        for (int i = 1; i < getCharacteristics().size(); ++i) {
            if (getCharacteristics().get(maxR) <= getCharacteristics().get(i)) {
                maxR = i;
            }
        }
        return maxR + 1;
    }

    protected double getEpsilon() {
        return mEpsilon;
    }

    protected void clear() {
        mTrials.clear();
        mCharacteristics.clear();
    }

    public TrialInterface getOpt() {
        int minT = 0;
        for (int i = 1; i < mTrials.size(); ++i) {
            if (mTrials.get(i).getY() < mTrials.get(minT).getY()) {
                minT = i;
            }
        }
        return mTrials.get(minT);
    }

    protected double M() {
        double M = 0.0;
        for (int i = 1; i < getTrials().size(); ++i) {
            double tempM =
                    Math.abs(getTrials().get(i).getY() - getTrials().get(i - 1).getY())
                            / (getTrials().get(i).getX() - getTrials().get(i - 1).getX());
            if (tempM > M) {
                M = tempM;
            }
        }
        return M;
    }


    public abstract BaseMethod call();
    protected abstract int recalculateCharacteristic();

}
