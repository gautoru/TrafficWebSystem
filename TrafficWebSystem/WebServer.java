import static spark.Spark.*;
import com.google.gson.*;
import controllers.TrafficAI;
import database.DatabaseHelper;
import java.util.HashMap;
import java.util.Map;

public class WebServer {
    public static void main(String[] args) {
        port(4567);
        staticFiles.externalLocation("web");
        DatabaseHelper.initializeDatabase();

        post("/predict", (req, res) -> {
            Gson gson = new Gson();
            Map<String, String> data = gson.fromJson(req.body(), Map.class);

            int north = Integer.parseInt(data.get("north"));
            int south = Integer.parseInt(data.get("south"));
            int east = Integer.parseInt(data.get("east"));
            int west = Integer.parseInt(data.get("west"));

            int totalVehicles = north + south + east + west;
            String suggestion = TrafficAI.getSuggestion(north, south, east, west);
            String congestion = TrafficAI.getCongestionLevel(totalVehicles);

            DatabaseHelper.logData(north, south, east, west, congestion, suggestion, totalVehicles);

            Map<String, String> response = new HashMap<>();
            response.put("suggestion", suggestion);
            response.put("congestion", congestion);

            res.type("application/json");
            return gson.toJson(response);
        });

        get("/logs", (req, res) -> {
            res.type("text/html");
            return DatabaseHelper.getLogsAsHtml();
        });
    }
}
