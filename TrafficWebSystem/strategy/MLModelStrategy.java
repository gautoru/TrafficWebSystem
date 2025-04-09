package strategy;

import model.TrafficModel;

public class MLModelStrategy implements SignalStrategy {

    @Override
    public int calculateSignalTime(int vehicleCount) {
        // Example logic
        return vehicleCount > 50 ? 60 : 30;
    }

    @Override
    public String predict(TrafficModel model) {
        // Dummy logic – you can call your actual ML model here
        int total = model.getNorth() + model.getSouth() + model.getEast() + model.getWest();
        return total > 100 ? "High" : "Low";
    }
}
