package server.plugin;


import java.util.Map;

public class PluginConfig {
    /**
     * Based on the command­line parameter for the name of the persistence plugin.
     */
    private String name;

    /**
     * Based on the configFile parameter for the name of the persistence plugin.
     */
    private String jarName;

    /**
     * Number of commands, N, between updates of the game state blobs.
     */
    private int updateNumber;
    /**
     * Type of plugin. Defaults to implement the IPersistenceFactory interface
     */
    private PluginType type = PluginType.PERSISTENCE;

    /**
     * Type of plugin. Defaults to implement the IPersistenceFactory interface
     */
    private Map otherArgs;

    /**
     * Default constructor
     *
     * @param name
     * @param jarName
     * @param type
     */
    public PluginConfig(String name, String jarName, PluginType type, Map otherArgs) {
        this.name = name;
        this.jarName = jarName;
        this.type = type;
        this.otherArgs = otherArgs;
    }

    public String getJarName() {
        return jarName;
    }

    public void setJarName(String jarName) {
        this.jarName = jarName;
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

    public Map getOtherArgs() {
        return otherArgs;
    }

    public void setOtherArgs(Map otherArgs) {
        this.otherArgs = otherArgs;
    }

    /**
     * Types of plugins.
     */
    public enum PluginType {
        PERSISTENCE
    }
}
