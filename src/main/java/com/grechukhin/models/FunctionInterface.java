package com.grechukhin.models;

import java.util.Map;

public interface FunctionInterface extends BaseModel {

    void setFunction(String function);
    void setInterval(Double leftX, Double rightX);
    String getFunctionAsString();
    double calculate(double x);
    Map<Double, Double> calculateAll(double step);

    interface FunctionListener extends BaseListener {
        void onFunctionChanged();
        void onFinishCalculation(double x, double y);
        void onFinishCalculationAll(Map<Double, Double> values);
    }

    Double defaultLeftX = -3d;
    Double defaultRight = 3d;
    Double defaultEpsilon = 0.01;
    Double defaultR = 2d;
    Integer defaultSource = 500;
}
