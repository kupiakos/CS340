package server.plugin;

import java.util.ArrayList;

/**
 * Plugins are be bundled into JAR files.
 */
public interface IPluginLoader {
    /**
     * Opens the plugin JAR file that the PluginConfig name specified and then
     * uses an attribute from JAR manifest or the list of all files in the JAR file to find
     * the class that implements the Plugin interface.
     * The server will instantiate plugin objects by putting plugin JAR
     * files on its classpath using URLClassLoader, and then use the Class.forName method to
     * load the class, and the Class.newInstance method to create them.
     */
    ArrayList<IPlugin> loadConfig(ArrayList<PluginConfig> configs);

    /**
     * Calls create() for each plugin
     *
     * @param plugins list of non-activated plugins
     * @return list of activated plugins
     */
    ArrayList<IPlugin> startPlugins(ArrayList<IPlugin> plugins);

    /**
     * Calls all needed code to turn off / disable each plugin
     *
     * @param plugins list of activated plugins
     * @return list of plugins that turned off correctly
     */
    ArrayList<IPlugin> stopPlugins(ArrayList<IPlugin> plugins);

    /**
     * Based on the command­line parameter for the name of the persistence plugin and
     * number of commands, N, between updates of the game state blobs
     * <p>
     * Can either take a config file or command line args
     *
     * @param o config object
     * @return list of pluginConfigs
     */
    ArrayList<PluginConfig> parseConfig(Object o);
}
