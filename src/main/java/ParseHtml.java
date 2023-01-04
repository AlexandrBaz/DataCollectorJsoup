import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ParseHtml {
    static TreeMap<String, String> metroLines = new TreeMap<>();
    static TreeMap<String, List<String>> stationsOnTheLine = new TreeMap<>();
    static TreeSet<String> connectionsOnTheLine = new TreeSet<>();

    public static void parseHtml(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        getStation(doc);
        getLines(doc);
        getConnections(doc);
    }

    private static void getLines(Document doc) {
        Elements stationLines = doc.select("span.js-metro-line");
        stationLines.forEach(stationLine ->
                metroLines.put(stationLine.attr("data-line"), stationLine.text()));
    }

    private static void getStation(Document doc) {
        Element div = doc.select("div.t-text-simple").first();
        assert div != null;
        Elements lines = div.select("div.js-depend");
        lines.forEach(line -> {
            List<String> nameStations;
            Elements stationName = line.select("span.name");
            String lineNum = line.attr("data-depend-set").replaceAll("lines-", "");
            nameStations = stationName.stream().map(Element::text).collect(Collectors.toList());
            stationsOnTheLine.put(lineNum, nameStations);
        });
    }

    private static void getConnections(Document doc) {
        Elements body = doc.select(".js-depend");
        Elements divLines = body.select("div.js-metro-stations");
        divLines.forEach(divs -> {
            Elements pTags = divs.select("p");
            String numberLineFrom = divs.attr("data-line");//done
            pTags.forEach(pTag -> {
                String stationFrom = pTag.text().replaceFirst("^\\d+.\\p{Z}+", "");
                TreeSet<String> stationConnect = new TreeSet<>();
                if (pTag.select("span + span + span").hasClass("t-icon-metroln")) {
                    Elements pStation = pTag.select(".t-icon-metroln");
                    stationConnect.add(stationFrom + "|" + numberLineFrom);
                    pStation.forEach(pStat -> {
                        String numberLineTo = pStat.attr("class").replaceAll("t-icon-metroln ln-", "");//done
                        String stationTo = pStat.attr("title").replaceAll("^\\W+«", "").replaceAll("»\\W+$", "");//done
                        stationConnect.add(stationTo + "|" + numberLineTo);
                    });
                    connectionsOnTheLine.add(stationConnect.toString());
                }
            });
        });
    }

    public static TreeMap<String, String> getMetroLines() {
        return metroLines;
    }

    public static TreeMap<String, List<String>> getStationsOnTheLine() {
        return stationsOnTheLine;
    }

    public static TreeSet<String> getConnectionsOnTheLine() {
        return connectionsOnTheLine;
    }

}
