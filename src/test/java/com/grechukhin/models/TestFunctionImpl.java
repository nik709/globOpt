package com.grechukhin.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class TestFunctionImpl {

    @Mock
    private FunctionInterface.FunctionListener mListener;

    @Mock
    private ModelFactory mFactory;

    @Before
    public void setUp() {
        initMocks(this);
        when(mFactory.createModel(FunctionInterface.class))
                .thenReturn(new FunctionImpl());
    }

    @Test
    public void canConstruct() {
        FunctionInterface function = (FunctionInterface) mFactory.createModel(FunctionInterface.class);
        Assert.assertNotNull(function);
    }

    @Test
    public void canRegisterListener() {
        FunctionInterface function = (FunctionInterface) mFactory.createModel(FunctionInterface.class);
        function.registerListener(mListener);

        function.setFunction("x*x");
        verify(mListener, atLeastOnce()).onFunctionChanged();

        function.calculate(2.0);
        verify(mListener, atLeastOnce()).onFinishCalculation(2.0, 4.0);

        function.setInterval(1d , 2d);
        function.calculateAll(0.1);
        verify(mListener, atLeastOnce()).onFinishCalculationAll(anyMap());

    }

    @Test
    public void canCalculateSin() {
        String sin = "sin(x)";
        FunctionInterface function = (FunctionInterface) mFactory.createModel(FunctionInterface.class);

        function.setFunction(sin);

        Assert.assertEquals(1, function.calculate(Math.PI/2), 0.1);
        Assert.assertEquals(0, function.calculate(Math.PI), 0.1);
        Assert.assertEquals(-1, function.calculate(-Math.PI/2), 0.1);
        Assert.assertEquals(Math.sqrt(2) / 2, function.calculate(Math.PI/4), 0.1);


    }

}
