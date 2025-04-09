package database;

import java.sql.*;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:traffic.db";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection()) {
            String createTable = "CREATE TABLE IF NOT EXISTS logs (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "north INTEGER, south INTEGER, east INTEGER, west INTEGER," +
                    "congestion TEXT, suggestion TEXT," +
                    "timestamp TEXT, vehicle_count INTEGER)";
            conn.createStatement().execute(createTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void logData(int north, int south, int east, int west,
                                String congestion, String suggestion, int vehicleCount) {
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO logs (north, south, east, west, congestion, suggestion, timestamp, vehicle_count) " +
                         "VALUES (?, ?, ?, ?, ?, ?, datetime('now', 'localtime'), ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, north);
            stmt.setInt(2, south);
            stmt.setInt(3, east);
            stmt.setInt(4, west);
            stmt.setString(5, congestion);
            stmt.setString(6, suggestion);
            stmt.setInt(7, vehicleCount);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getLogsAsHtml() {
        StringBuilder html = new StringBuilder();
        html.append("<div style='background:#fff8dc; border-radius:10px; padding:20px;'>");
        html.append("<h2>Traffic Signal Logs</h2>");
        html.append("<table border='1'><tr><th>North</th><th>South</th><th>East</th><th>West</th><th>Congestion</th><th>Suggestion</th><th>Timestamp</th><th>Vehicle Count</th></tr>");
        try (Connection conn = getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM logs ORDER BY id DESC");
            while (rs.next()) {
                html.append("<tr>");
                html.append("<td>").append(rs.getInt("north")).append("</td>");
                html.append("<td>").append(rs.getInt("south")).append("</td>");
                html.append("<td>").append(rs.getInt("east")).append("</td>");
                html.append("<td>").append(rs.getInt("west")).append("</td>");
                html.append("<td>").append(rs.getString("congestion")).append("</td>");
                html.append("<td>").append(rs.getString("suggestion")).append("</td>");
                html.append("<td>").append(rs.getString("timestamp")).append("</td>");
                html.append("<td>").append(rs.getInt("vehicle_count")).append("</td>");
                html.append("</tr>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        html.append("</table></div>");
        return html.toString();
    }
}
