package controllers;

public class TrafficAI {
    public static String getSuggestion(int north, int south, int east, int west) {
        int ew = east + west;
        int ns = north + south;

        if (ew > ns) {
            return "Open East-West signal longer.";
        } else if (ns > ew) {
            return "Open North-South signal longer.";
        } else {
            return "Keep all signals balanced.";
        }
    }

    public static String getCongestionLevel(int totalVehicles) {
        if (totalVehicles > 200) return "High";
        if (totalVehicles > 100) return "Moderate";
        return "Low";
    }
}
