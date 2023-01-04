public class Main {

    public static void main(String[] args) throws Exception {
        String url = "https://skillbox-java.github.io/";
        String path = "DataIn";
        ParseHtml.parseHtml(url);
        ParseFiles.getUri(path);
        CreateStation.createStation();
        CreateMetro.createMetro();
        CreateJsonFile.createJson("DataOut/map.json","DataOut/station.json");
    }
}
