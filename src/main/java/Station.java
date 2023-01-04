import com.fasterxml.jackson.annotation.*;
@JsonRootName(value = "Station", namespace = "name")
public class Station {
    String name;
    @JsonIgnore
    String lineNumber;
    String line;
    String date;
    Double depths;
    Boolean hasConnection;

    public Station(String name, String lineNumber, String line) {
        this.name = name;
        this.lineNumber = lineNumber;
        this.line = line;
    }

    public String getName() {
        return name;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public String getLine() {
        return line;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Double getDepths() {
        return depths;
    }

    public void setDepths(Double depths) {
        this.depths = depths;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Boolean getHasConnection() {
        return hasConnection;
    }

    public void setHasConnection(Boolean hasConnection) {
        this.hasConnection = hasConnection;
    }

    @Override
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String toString() {
        return "Station{" +
                "name='" + name + '\'' +
                ", lineNumber='" + lineNumber + '\'' +
                ", nameLine='" + line + '\'' +
                ", dateFounded=" + date +
                ", stationDepths=" + depths +
                ", connection=" + hasConnection +
                '}';
    }

}