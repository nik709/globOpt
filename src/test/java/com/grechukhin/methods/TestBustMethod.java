package com.grechukhin.methods;

import com.grechukhin.models.FunctionInterface;
import com.grechukhin.models.ModelFactory;
import org.junit.Assert;
import org.junit.Test;

public class TestBustMethod {

    private final double mA = -1;
    private final double mB = 1;
    private final double mEpsilon = 0.001d;
    private final double mResEpsilon = 0.01d;

    private final int mSource = 50;

    private final String mEasyParabola = "x*x";
    private final String mEasySin = "sin(x)";


    @Test
    public void canCallOnEasyParabola() {
        Assert.assertEquals(0, callWithFunc(mEasyParabola), mResEpsilon);
    }

    @Test
    public void canCallOnEasySin() {
        Assert.assertEquals(-0.84, callWithFunc(mEasySin), mResEpsilon);
    }

    private double callWithFunc(String func) {
        FunctionInterface function = (FunctionInterface) ModelFactory.createModel(FunctionInterface.class);
        Assert.assertNotNull(function);
        function.setFunction(func);

        BaseMethod method = new BustMethod(mA, mB, mSource, mEpsilon);
        method.setFunction(function);
        method.call();

        return method.getOpt().getY();
    }
}
