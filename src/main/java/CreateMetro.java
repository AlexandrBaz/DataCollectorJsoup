import java.util.*;

public class CreateMetro {
    static List<StationOnTheLine> lineStations;
    static ArrayList<Object> connectionsStations;
    static List<MetroLines> metroLine;
    static Metro metro;

    public static void createMetro() {
        Map<String, List<String>> stationOnTheLine = ParseHtml.getStationsOnTheLine();
        putStationOnTheLine(stationOnTheLine);
        Set<String> connectionsOnTheLine = ParseHtml.getConnectionsOnTheLine();
        putConnections(connectionsOnTheLine);
        Map<String, String> metroLines = ParseHtml.getMetroLines();
        putMetroLines(metroLines);
        metro = new Metro(lineStations, connectionsStations, metroLine);

    }

    private static void putStationOnTheLine(Map<String, List<String>> stationOnTheLine) {
        lineStations = new ArrayList<>();
        stationOnTheLine.forEach((lineNumber, station) -> {
            StationOnTheLine line = new StationOnTheLine(lineNumber, station);
            lineStations.add(line);
        });
    }

    private static void putConnections(Set<String> connectionsOnTheLine) {
        connectionsStations = new ArrayList<>();
        connectionsOnTheLine.forEach(connections -> {
            String temp = connections.replaceAll("\\[", "").replaceAll("]", "");
            String[] fragments = temp.split(",");
            List<List<ConnectionStation>> connects = new ArrayList<>();
            List<ConnectionStation> stationConnect;
            for (String fragment : fragments) {
                stationConnect = new ArrayList<>();
                String[] splitConnection = fragment.split("\\|");
                ConnectionStation connect = new ConnectionStation(splitConnection[1].trim(), splitConnection[0].trim());
                stationConnect.add(connect);
                connects.add(stationConnect);
            }
            FullConnections fullConnections = new FullConnections(connects);
            connectionsStations.add(fullConnections);
        });
    }

    private static void putMetroLines(Map<String, String> metroLines) {
        metroLine = new ArrayList<>();
        metroLines.forEach((lines, nameLines) -> {
            MetroLines line = new MetroLines(lines, nameLines);
            metroLine.add(line);
        });
    }

    public static Metro getMetro() {
        return metro;
    }
}
