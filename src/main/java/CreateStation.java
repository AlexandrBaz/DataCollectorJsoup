import java.util.*;

public class CreateStation {
    public static List<Station> finalStation;
    static ArrayList<Object> stations = new ArrayList<>();

    public static void createStation() {
        TreeMap<String, List<String>> stationsOnTheLine = ParseHtml.getStationsOnTheLine();
        stationsOnTheLine.forEach((lineNumber, names) -> names.forEach(nameStation -> putMetroLines(lineNumber, nameStation)));
    }

    private static void putMetroLines(String lineNumber, String nameStation) {
        TreeMap<String, String> metroLines = ParseHtml.getMetroLines();
        metroLines.forEach((key, lineNames) -> {
            if (key.equals(lineNumber)) {
                finalStation = addStation(lineNumber, nameStation, lineNames);
                putDate();
            }
        });
    }

    private static List<Station> addStation(String lineNumber, String nameStation, String lineName) {
        List<Station> stations = new ArrayList<>();
        Station station = new Station(nameStation, lineNumber, lineName);
        stations.add(station);
        return stations;
    }

    private static void putDate() {
        TreeMap<String, String> stationsFoundDate = ParseFiles.getStationsFoundedDate();
        finalStation.forEach(stationFromStation -> stationsFoundDate.forEach((name, date) -> {
            if (name.equals(stationFromStation.getName())) {
                stationFromStation.setDate(date);
            }
        }));
        putDepths(finalStation);
    }

    private static void putDepths(List<Station> finalStation) {
        TreeMap<String, Double> stationsDepths = ParseFiles.getStationsDepths();
        finalStation.forEach(stationFromStation -> stationsDepths.forEach((name, depths) -> {
            if (name.equals(stationFromStation.getName())) {
                stationFromStation.setDepths(depths);
            }
        }));
        putHasConnection(finalStation);
    }

    private static void putHasConnection(List<Station> finalStation) {
        TreeSet<String> connectionsOnTheLine = ParseHtml.getConnectionsOnTheLine();
        finalStation.forEach(stationFromStation -> connectionsOnTheLine.forEach(connection -> {
            if (connection.contains(stationFromStation.getName() + "|" + stationFromStation.getLineNumber())) {
                stationFromStation.setHasConnection(true);
            }
        }));
        stations.add(finalStation);
    }

    public static ArrayList<Object> getStations() {
        return stations;
    }
}

