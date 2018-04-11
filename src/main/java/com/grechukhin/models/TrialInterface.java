package com.grechukhin.models;

public interface TrialInterface extends BaseModel {

    void setData(double x, double y);
    double getX();
    double getY();

    interface TrialListener extends BaseListener {
        void onTrialFinished(double y);
    }

}
