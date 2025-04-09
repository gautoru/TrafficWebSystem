package strategy;

import model.TrafficModel;

public interface SignalStrategy {
    int calculateSignalTime(int vehicleCount);
    String predict(TrafficModel model); // Add this method
}
