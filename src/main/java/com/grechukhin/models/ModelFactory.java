package com.grechukhin.models;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

public class ModelFactory {

    private static ModelFactory mInstance;

    private ModelFactory() {}

    public static ModelFactory getInstance() {
        if (mInstance != null) {
            return mInstance;
        }
        mInstance = new ModelFactory();
        return mInstance;
    }

    @Nullable
    public BaseModel createModel(@NotNull Class clazz) {
        if (clazz.equals(FunctionInterface.class)) {
            return new FunctionImpl();
        }

        if (clazz.equals(TrialInterface.class)) {
            return new Trial();
        }

        return null;
    }
}
