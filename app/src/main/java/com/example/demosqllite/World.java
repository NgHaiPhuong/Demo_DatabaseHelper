package com.example.demosqllite;

import androidx.annotation.NonNull;

public class World {
    private int mID;
    private String mWorld;
    private String mMean;

    public World(){

    }

    public World(String word, String mean) {
        this.mWorld = word;
        this.mMean = mean;
    }

    public World(int mID, String mWorld, String mMean) {
        this.mID = mID;
        this.mWorld = mWorld;
        this.mMean = mMean;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmWorld() {
        return mWorld;
    }

    public void setmWorld(String mWorld) {
        this.mWorld = mWorld;
    }

    public String getmMean() {
        return mMean;
    }

    public void setmMean(String mMean) {
        this.mMean = mMean;
    }

    @NonNull
    @Override
    public String toString() {
        return mID + " - " + mWorld + " - " + mMean;
    }
}
