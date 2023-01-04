import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class CreateJsonFile {
    static List<Object> station;
    static Object fullMetro;

    public static void createJson(String mapFile, String stationFile){
        try {
            writeStation(stationFile);
            createMap(mapFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createMap (String filename) {
        fullMetro = CreateMetro.getMetro();
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File(filename), fullMetro);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeStation(String filename) {
        station = CreateStation.getStations();
        try {
            ObjectMapper mapper = new ObjectMapper();
//            Files.writeString(Paths.get(filename), new JSONObject().put("stations", mapper).toString(4)); // Совет преподователя
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File(filename), station);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
// toDo дописать чтение из файла