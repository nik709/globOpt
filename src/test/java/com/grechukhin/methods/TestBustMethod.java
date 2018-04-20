package com.grechukhin.methods;

import org.junit.Assert;
import org.junit.Test;

import static com.grechukhin.TestMethodHelper.*;

public class TestBustMethod {


    @Test
    public void canCallOnEasyParabola() {
        Assert.assertEquals(0, callWithFunc(sEasyParabola, new BustMethod(sA, sB, sSmallSource, sEpsilon)), sResEpsilon);
    }

    @Test
    public void canCallOnEasySin() {
        Assert.assertEquals(-0.84, callWithFunc(sEasySin, new BustMethod(sA, sB, sSmallSource, sEpsilon)), sResEpsilon);
    }

    @Test
    public void canCallWithSmallIterations() {
        Assert.assertNotEquals(
                -8.89, callWithFunc(sHardTrigFunc, new BustMethod(-5, 5, 10, 0.001)), sResEpsilon
        );
    }
}
