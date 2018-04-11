package com.grechukhin.models;

import com.grechukhin.OperationExecutor;

import java.util.*;

public class FunctionImpl implements FunctionInterface {

    private class Interval {
        double leftX;
        double rightX;

        Interval(Double leftX, Double rightX) {
            if (leftX == null || rightX == null) {
                throw new IllegalArgumentException("Arguments can not be null");
            }
            this.leftX = leftX;
            this.rightX = rightX;
        }
    }

    private String function;
    private Interval interval;
    private List<FunctionListener> listeners = new ArrayList<>();

    FunctionImpl() {}

    @Override
    public void setFunction(String function) {
        this.function = function;

        for (FunctionListener listener : listeners) {
            listener.onFunctionChanged();
        }
    }

    @Override
    public void setInterval(Double leftX, Double rightX) {
        this.interval = new Interval(leftX, rightX);
    }

    @Override
    public String getFunctionAsString() {
        return function;
    }

    @Override
    public double calculate(double x) {
        String f = function
                .replaceAll("x", String.valueOf(x))
                .replaceAll("√", "sqrt");

        double result = OperationExecutor.eval(f);

        for (FunctionListener listener : listeners) {
            listener.onFinishCalculation(result);
        }
        return result;
    }

    @Override
    public Map<Double, Double> calculateAll(double step) {
        Map<Double, Double> result = new TreeMap<>();
        for (double x = interval.leftX; x < interval.rightX; x+=step) {
            String f = function
                    .replaceAll("x", String.valueOf(x))
                    .replaceAll("√", "sqrt");
            result.put(x, OperationExecutor.eval(f));
        }

        for (FunctionListener listener : listeners) {
            listener.onFinishCalculationAll(result);
        }

        return result;
    }

    @Override
    public void registerListener(BaseListener listener) {
        if (!listeners.contains((FunctionListener) listener)) {
            listeners.add((FunctionListener) listener);
        }
    }

    @Override
    public void unregisterListener(BaseListener listener) {
        listeners.remove((FunctionListener) listener);
    }

    @Override
    public void unregisterAll() {
        listeners.clear();
    }
}
