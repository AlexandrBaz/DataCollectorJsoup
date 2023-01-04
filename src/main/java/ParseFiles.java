import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

public class ParseFiles {
    public static TreeMap<String, Double> stationsDepths = new TreeMap<>();
    public static TreeMap<String, String> stationsFoundDate = new TreeMap<>();

    public static void getUri(String path) throws IOException, ParseException, java.text.ParseException {
        ArrayList<String> fileUri = new ArrayList<>();
        Files.walk(Paths.get(path))
                .filter(Files::isRegularFile)
                .filter(f -> !f.getFileName().endsWith(".DS_Store"))
                .map(Object::toString)
                .forEach(fileUri::add);
        for (String uri : fileUri) {
            if (uri.contains("json")) {
                parseJson(uri);
            } else {
                parseCSV(uri);
            }
        }
    }

    private static String getJsonFile(String uri) throws IOException {
        StringBuilder builder = new StringBuilder();
        List<String> lines = Files.readAllLines(Paths.get(uri));
        lines.forEach(builder::append);
        return builder.toString();
    }

    private static void parseJson(String uri) throws ParseException, IOException {
        JSONParser parser = new JSONParser();
        JSONArray jsonData = (JSONArray) parser.parse(getJsonFile(uri));
        jsonData.forEach(stationDepthsObject -> {
            String stationName;
            Object stationDepths;
            String stationFounded = "";
            String date = "";
            JSONObject stationJsonObject = (JSONObject) stationDepthsObject;
            if (uri.contains("depths-1")) {
                stationName = (String) stationJsonObject.get("name");
                stationDepths = stationJsonObject.get("depth");
                try {
                    Double putDepths = chekDepths(stationDepths.toString());
                    stationsDepths.put(stationName, putDepths);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            }
            if (uri.contains("depths-3")) {
                stationName = (String) stationJsonObject.get("station_name");
                stationDepths = stationJsonObject.get("depth_meters");
                try {
                    Double putDepths = chekDepths(stationDepths.toString());
                    stationsDepths.put(stationName, putDepths);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            }
            if (uri.contains("dates-2")) {
                stationFounded = (String) stationJsonObject.get("name");
                date = (String) stationJsonObject.get("date");
            }
            stationsFoundDate.put(stationFounded, date);
        });
    }

    private static void parseCSV(String uri) throws IOException, java.text.ParseException {
        List<String> lines = Files.readAllLines(Paths.get(uri));
        lines.remove(0);
        for (String line : lines) {
            String[] fragments = line.split(",");
            if (uri.contains("date")) {
                stationsFoundDate.put(fragments[0], fragments[1]);
            }
            if (uri.contains("depths")) {
                String depths;
                String name = fragments[0];
                if (fragments.length == 3) {
                    depths = fragments[1] + "," + fragments[2];
                } else {
                    depths = fragments[1];
                }
                Double putDepths = chekDepths(depths.replaceAll("\"", "")); // Принимает Double
                stationsDepths.put(name, putDepths);
            }
        }
    }

    private static Double chekDepths(String depths) throws java.text.ParseException {
        NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
        if (depths.contains("?")) {
            return null;
        }
        double clearDepths = 0;
        if (depths.startsWith("−")) {
            String depthsTemp = depths.replace("−", "").replaceAll(",", ",");
            Number number = format.parse(depthsTemp);
            clearDepths = (number.doubleValue()) * -1;
            return clearDepths;
        }
        if (!depths.contains("?") && !depths.startsWith("−")) {
            Number number = format.parse(depths);
            clearDepths = number.doubleValue();
            return clearDepths;
        }
        return clearDepths;
    }

    public static TreeMap<String, Double> getStationsDepths() {
        return stationsDepths;
    }

    public static TreeMap<String, String> getStationsFoundedDate() {
        return stationsFoundDate;
    }

}
