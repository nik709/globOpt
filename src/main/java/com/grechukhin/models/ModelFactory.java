package com.grechukhin.models;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

public class ModelFactory {

    @Nullable
    public static BaseModel createModel(@NotNull Class clazz) {
        if (clazz.equals(FunctionInterface.class)) {
            return new FunctionImpl();
        }

        if (clazz.equals(TrialInterface.class)) {
            return new Trial();
        }

        return null;
    }
}
