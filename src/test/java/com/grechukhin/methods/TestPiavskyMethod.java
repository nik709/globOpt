package com.grechukhin.methods;

import org.junit.Assert;
import org.junit.Test;

import static com.grechukhin.TestMethodHelper.*;

public class TestPiavskyMethod {

    @Test
    public void canCallOnEasyParabola() {
        Assert.assertEquals(0
                , callWithFunc(sEasyParabola, new PiavskyMethod(sA, sB, sEpsilon, sSmallSource, 3.0))
                , sResEpsilon);
    }

    @Test
    public void canCallOnEasySin() {
        Assert.assertEquals(-0.84
                , callWithFunc(sEasySin, new PiavskyMethod(sA, sB, sEpsilon, sSmallSource, 3.0))
                , sResEpsilon);
    }
}
