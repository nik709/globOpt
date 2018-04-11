package com.grechukhin.models;

import java.util.ArrayList;
import java.util.List;

public class Trial implements TrialInterface {

    private List<TrialListener> listeners = new ArrayList<>();
    private Double mX;
    private Double mY;

    Trial() {
    }

    @Override
    public void registerListener(BaseListener listener) {
        if (!listeners.contains((TrialListener) listener)) {
            listeners.add((TrialListener) listener);
        }
    }

    @Override
    public void unregisterListener(BaseListener listener) {
        listeners.remove((TrialListener) listener);
    }

    private void notifyAboutTaskFinished() {
        for (TrialListener listener : listeners) {
            listener.onTrialFinished(mY);
        }
    }

    @Override
    public void unregisterAll() {
        listeners.clear();
    }

    @Override
    public void setData(double x, double y) {
        mX = x;
        mY = y;
    }

    @Override
    public double getX() {
        return mX;
    }

    @Override
    public double getY() {
        return mY;
    }
}
