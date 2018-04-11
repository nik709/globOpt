package com.grechukhin.models;

public interface BaseModel {

    void registerListener(BaseListener listener);
    void unregisterListener(BaseListener listener);
    void unregisterAll();

    interface BaseListener {}
}
