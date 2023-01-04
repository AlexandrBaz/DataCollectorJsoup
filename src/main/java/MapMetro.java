import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "lineNumber")
class StationOnTheLine {
    String lineNumber;
    List<String> nameStation;

    public StationOnTheLine(String lineNumber, List<String> nameStation) {
        this.lineNumber = lineNumber;
        this.nameStation = nameStation;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public List<String> getNameStation() {
        return nameStation;
    }

    @Override
    public String toString() {
        return "Stations{" +
                "line='" + lineNumber + '\'' +
                ", name=" + nameStation +
                '}';
    }
}

class MetroLines {
    String line;
    String LineName;

    public MetroLines(String line, String lineName) {
        this.line = line;
        LineName = lineName;
    }

    public String getLine() {
        return line;
    }

    public String getLineName() {
        return LineName;
    }

    @Override
    public String toString() {
        return "lines{" +
                "line='" + line + '\'' +
                ", name='" + LineName + '\'' +
                '}';
    }
}

class ConnectionStation {
    String lineNumber;
    String stationName;

    public ConnectionStation(String lineNumber, String stationName) {
        this.lineNumber = lineNumber;
        this.stationName = stationName;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public String getStationName() {
        return stationName;
    }

    @Override
    public String toString() {
        return "{" +
                "lineNumber='" + lineNumber + '\'' +
                ", stationName='" + stationName + '\'' +
                '}';
    }
}

class FullConnections {

    List<List<ConnectionStation>> fullConnections;

    public FullConnections(List<List<ConnectionStation>> fullConnections) {
        this.fullConnections = fullConnections;
    }

    public List<List<ConnectionStation>> getFullConnections() {
        return fullConnections;
    }

    @Override
    public String toString() {
        return "FullConnections{" +
                "fullConnections=" + fullConnections +
                '}';
    }
}

class Metro {
    List<StationOnTheLine> lineStations;
    ArrayList<Object> connectionsStations;
    List<MetroLines> metroLine;

    public Metro(List<StationOnTheLine> lineStations, ArrayList<Object> connectionsStations, List<MetroLines> metroLine) {
        this.lineStations = lineStations;
        this.connectionsStations = connectionsStations;
        this.metroLine = metroLine;
    }

    public List<StationOnTheLine> getLineStations() {
        return lineStations;
    }

    public ArrayList<Object> getConnectionsStations() {
        return connectionsStations;
    }

    public List<MetroLines> getMetroLine() {
        return metroLine;
    }

    @Override
    public String toString() {
        return "Metro{" +
                "lineStations=" + lineStations +
                ", connectionsStations=" + connectionsStations +
                ", metroLine=" + metroLine +
                '}';
    }
}