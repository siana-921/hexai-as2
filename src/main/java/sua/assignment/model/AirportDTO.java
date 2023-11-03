package sua.assignment.model;

public class AirportDTO {
    private String icao;
    private String iata;
    private String name;
    private String city;
    private String state;
    private String country;
    private int elevation;
    private double lat;
    private double lon;
    private String tz;

    public String getIcao() {
        return icao;
    }
    public String getIata() {
        return iata;
    }
    public String getName() {
        return name;
    }
    public String getCity() {
        return city;
    }
    public String getState() {
        return state;
    }
    public String getCountry() {
        return country;
    }
    public int getElevation() {
        return elevation;
    }
    public double getLat(){
        return lat;
    }
    public double getLon(){
        return lon;
    }
    public String getTz() {
        return tz;
    }
}
