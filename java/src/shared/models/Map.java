package shared.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Map {

    @SerializedName("hexes")
    @Expose
    private List<Hex> hexes = new ArrayList<Hex>();
    @SerializedName("ports")
    @Expose
    private List<Port> ports = new ArrayList<Port>();
    @SerializedName("roads")
    @Expose
    private List<Road> roads = new ArrayList<Road>();
    @SerializedName("settlements")
    @Expose
    private List<Settlement> settlements = new ArrayList<Settlement>();
    @SerializedName("cities")
    @Expose
    private List<City> cities = new ArrayList<City>();
    @SerializedName("radius")
    @Expose
    private String radius;
    @SerializedName("robber")
    @Expose
    private Robber robber;

    /**
     * No args constructor for use in serialization
     */
    public Map() {
    }

    /**
     * @param ports
     * @param cities
     * @param settlements
     * @param hexes
     * @param radius
     * @param robber
     * @param roads
     */
    public Map(List<Hex> hexes, List<Port> ports, List<Road> roads, List<Settlement> settlements, List<City> cities, String radius, Robber robber) {
        this.hexes = hexes;
        this.ports = ports;
        this.roads = roads;
        this.settlements = settlements;
        this.cities = cities;
        this.radius = radius;
        this.robber = robber;
    }

    /**
     * @return The hexes
     */
    public List<Hex> getHexes() {
        return hexes;
    }

    /**
     * @param hexes The hexes
     */
    public void setHexes(List<Hex> hexes) {
        this.hexes = hexes;
    }

    public Map withHexes(List<Hex> hexes) {
        this.hexes = hexes;
        return this;
    }

    /**
     * @return The ports
     */
    public List<Port> getPorts() {
        return ports;
    }

    /**
     * @param ports The ports
     */
    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }

    public Map withPorts(List<Port> ports) {
        this.ports = ports;
        return this;
    }

    /**
     * @return The roads
     */
    public List<Road> getRoads() {
        return roads;
    }

    /**
     * @param roads The roads
     */
    public void setRoads(List<Road> roads) {
        this.roads = roads;
    }

    public Map withRoads(List<Road> roads) {
        this.roads = roads;
        return this;
    }

    /**
     * @return The settlements
     */
    public List<Settlement> getSettlements() {
        return settlements;
    }

    /**
     * @param settlements The settlements
     */
    public void setSettlements(List<Settlement> settlements) {
        this.settlements = settlements;
    }

    public Map withSettlements(List<Settlement> settlements) {
        this.settlements = settlements;
        return this;
    }

    /**
     * @return The cities
     */
    public List<City> getCities() {
        return cities;
    }

    /**
     * @param cities The cities
     */
    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public Map withCities(List<City> cities) {
        this.cities = cities;
        return this;
    }

    /**
     * @return The radius
     */
    public String getRadius() {
        return radius;
    }

    /**
     * @param radius The radius
     */
    public void setRadius(String radius) {
        this.radius = radius;
    }

    public Map withRadius(String radius) {
        this.radius = radius;
        return this;
    }

    /**
     * @return The robber
     */
    public Robber getRobber() {
        return robber;
    }

    /**
     * @param robber The robber
     */
    public void setRobber(Robber robber) {
        this.robber = robber;
    }

    public Map withRobber(Robber robber) {
        this.robber = robber;
        return this;
    }

}
