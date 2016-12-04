package server.plugin;


public class PluginConfig {
    /**
     * Based on the command­line parameter for the name of the persistence plugin.
     * We will use this to match with the .jar file.
     */
    private String name;

    /**
     * Number of commands, N, between updates of the game state blobs.
     */
    private int updateNumber;
    /**
     * Type of plugin. Defaults to implement the IPersistenceFactory interface
     */
    private PluginType type = PluginType.PERSISTENCE;

    /**
     * Default constructor
     *
     * @param name
     * @param updateNumber
     */
    public PluginConfig(String name, int updateNumber) {
        this.name = name;
        this.updateNumber = updateNumber;
    }

    /**
     * Optional constructor
     *
     * @param name
     * @param updateNumber
     * @param type
     */
    public PluginConfig(String name, int updateNumber, PluginType type) {
        this.name = name;
        this.updateNumber = updateNumber;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUpdateNumber() {
        return updateNumber;
    }

    public void setUpdateNumber(int updateNumber) {
        this.updateNumber = updateNumber;
    }

    public PluginType getType() {
        return type;
    }

    public void setType(PluginType type) {
        this.type = type;
    }

    /**
     * Types of plugins.
     */
    public enum PluginType {
        PERSISTENCE
    }
}
