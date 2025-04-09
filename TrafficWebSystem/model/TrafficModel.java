package model;

import strategy.SignalStrategy;


public class TrafficModel {
    private int north;
    private int south;
    private int east;
    private int west;

    private SignalStrategy strategy;

    public TrafficModel(int north, int south, int east, int west) {
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }

    // Add getters
    public int getNorth() { return north; }
    public int getSouth() { return south; }
    public int getEast() { return east; }
    public int getWest() { return west; }

    public void setStrategy(SignalStrategy strategy) {
        this.strategy = strategy;
    }

    public int executeStrategy() {
        int vehicleCount = north + south + east + west;
        return strategy.calculateSignalTime(vehicleCount);
    }

    public String runPrediction() {
        return strategy.predict(this);
    }
}
