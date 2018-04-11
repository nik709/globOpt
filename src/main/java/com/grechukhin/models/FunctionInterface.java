package com.grechukhin.models;

import java.util.List;
import java.util.Map;

public interface FunctionInterface extends BaseModel {

    void setFunction(String function);
    void setInterval(Double leftX, Double rightX);
    String getFunctionAsString();
    double calculate(double x);
    Map<Double, Double> calculateAll(double step);

    interface FunctionListener extends BaseListener {
        void onFunctionChanged();
        void onFinishCalculation(double value);
        void onFinishCalculationAll(Map<Double, Double> values);
    }

    Double defaultLeftX = 0d;
    Double defaultRight = 1d;
}
