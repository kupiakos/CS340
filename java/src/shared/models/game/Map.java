package shared.models.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;
import java.util.List;
import java.util.ArrayList;

import com.sun.istack.internal.NotNull;
import shared.locations.HexLocation;

@Generated("net.kupiakos")
public class Map {

    @SerializedName("roads")
    @Expose
    private List<Road> roads = new ArrayList<Road>();

    @SerializedName("radius")
    @Expose
    private int radius;

    @SerializedName("robber")
    @Expose
    private HexLocation robber;

    @SerializedName("hexes")
    @Expose
    private List<Hex> hexes = new ArrayList<Hex>();

    @SerializedName("ports")
    @Expose
    private List<Port> ports = new ArrayList<Port>();

    @SerializedName("settlements")
    @Expose
    private List<VertexObject> settlements = new ArrayList<VertexObject>();

    @SerializedName("cities")
    @Expose
    private List<VertexObject> cities = new ArrayList<VertexObject>();


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public Map() {
    }

    /**
      * @param roads The list of roads currently placed on the map
      * @param radius The radius of the map (it includes the center hex, and the ocean hexes)
      * @param robber The current location of the robber
      * @param hexes A list of all the hexes on the grid - it's only land tiles
      * @param ports The list of ports currently placed on the map
      * @param settlements The list of settlements currently placed on the map
      * @param cities The list of cities currently placed on the map
     */
    public Map(List<Road> roads, int radius, HexLocation robber, List<Hex> hexes, List<Port> ports, List<VertexObject> settlements, List<VertexObject> cities) {
            this.roads = roads;
            this.radius = radius;
            this.robber = robber;
            this.hexes = hexes;
            this.ports = ports;
            this.settlements = settlements;
            this.cities = cities;
    }

    /**
     * @return the list of roads currently placed on the map
     */
    public List<Road> getRoads() { return roads; }

    /**
     * @param roads list of roads currently placed on the map
     */
    public void setRoads(@NotNull List<Road> roads) { this.roads = roads; }

    public Map withRoads(@NotNull List<Road> roads) {
        setRoads(roads);
        return this;
    }
    /**
     * @return The radius of the map (it includes the center hex, and the ocean hexes; pass this into the hexgrid constructor)
     */
    public int getRadius() { return radius; }

    /**
     * @param radius The radius of the map (it includes the center hex, and the ocean hexes; pass this into the hexgrid constructor)
     */
    public void setRadius(int radius) { this.radius = radius; }

    public Map withRadius(int radius) {
        setRadius(radius);
        return this;
    }
    /**
     * @return The current location of the robber
     */
    public HexLocation getRobber() { return robber; }

    /**
     * @param robber The current location of the robber
     */
    public void setRobber(@NotNull HexLocation robber) { this.robber = robber; }

    public Map withRobber(@NotNull HexLocation robber) {
        setRobber(robber);
        return this;
    }
    /**
     * @return A list of all the hexes on the grid - it's only land tiles
     */
    public List<Hex> getHexes() { return hexes; }

    /**
     * @param hexes A list of all the hexes on the grid - it's only land tiles
     */
    public void setHexes(@NotNull List<Hex> hexes) { this.hexes = hexes; }

    public Map withHexes(@NotNull List<Hex> hexes) {
        setHexes(hexes);
        return this;
    }
    /**
     * @return the list of ports currently placed on the map
     */
    public List<Port> getPorts() { return ports; }

    /**
     * @param ports list of ports currently placed on the map
     */
    public void setPorts(@NotNull List<Port> ports) { this.ports = ports; }

    public Map withPorts(@NotNull List<Port> ports) {
        setPorts(ports);
        return this;
    }
    /**
     * @return the list of settlements currently placed on the map
     */
    public List<VertexObject> getSettlements() { return settlements; }

    /**
     * @param settlements list of settlements currently placed on the map
     */
    public void setSettlements(@NotNull List<VertexObject> settlements) { this.settlements = settlements; }

    public Map withSettlements(@NotNull List<VertexObject> settlements) {
        setSettlements(settlements);
        return this;
    }
    /**
     * @return the list of cities currently placed on the map
     */
    public List<VertexObject> getCities() { return cities; }

    /**
     * @param cities list of cities currently placed on the map
     */
    public void setCities(@NotNull List<VertexObject> cities) { this.cities = cities; }

    public Map withCities(@NotNull List<VertexObject> cities) {
        setCities(cities);
        return this;
    }

    @Override
    public String toString() {
        return "Map [" +
            "roads=" + roads +
            ", radius=" + radius +
            ", robber=" + robber +
            ", hexes=" + hexes +
            ", ports=" + ports +
            ", settlements=" + settlements +
            ", cities=" + cities +
            "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Map) {
            return equals((Map)other);
        }
        return false;
    }

    public boolean equals(Map other) {
        return (
            roads == other.roads &&
            radius == other.radius &&
            robber == other.robber &&
            hexes == other.hexes &&
            ports == other.ports &&
            settlements == other.settlements &&
            cities == other.cities
        );
    }
}
