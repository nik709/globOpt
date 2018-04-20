package com.grechukhin;

import com.grechukhin.methods.BaseMethod;
import com.grechukhin.methods.BustMethod;
import com.grechukhin.models.FunctionInterface;
import com.grechukhin.models.ModelFactory;
import org.junit.Assert;

import java.util.Random;

public class TestMethodHelper {
    public static final double sA = -1;
    public static final double sB = 1;
    public static final double sEpsilon = 0.001d;
    public static final double sResEpsilon = 0.01d;

    public static final int sSmallSource = 50;
    public static final int sMediumSource = 500;
    public static final int sBigSource = 1000;

    public static final String sEasyParabola = "x*x";
    public static final String sEasySin = "sin(x)";
    public static final String sHardTrigFunc = "5*sin(3*x)+4*cos(7*x)";

    public static double callWithFunc(String func, BaseMethod method) {
        FunctionInterface function = (FunctionInterface) ModelFactory.getInstance().createModel(FunctionInterface.class);
        Assert.assertNotNull(function);
        function.setFunction(func);

        method.setFunction(function);
        method.call();

        return method.getOpt().getY();
    }
}
