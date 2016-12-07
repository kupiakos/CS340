package server.plugin;

import java.io.File;
import java.util.List;

/**
 * Plugins are be bundled into JAR files.
 */
public interface IPluginLoader {
    /**
     * Opens the plugin JAR file that the PluginConfig name specified and then
     * uses an attribute from JAR manifest or the list of all files in the JAR file to find
     * the class that implements the IPlugin interface.
     * The server will instantiate plugin objects by putting plugin JAR
     * files on its classpath using URLClassLoader, and then use the Class.forName method to
     * load the class, and the Class.newInstance method to create them.
     *
     * @param configs
     * @param folder path to the folder where the plugin .jars are located
     * @return
     */
    List<IPlugin> loadConfig(List<PluginConfig> configs, File folder);

    /**
     * Calls create() for each plugin
     *
     * @param plugins list of non-activated plugins
     * @return list of activated plugins
     */
    List<IPlugin> startPlugins(List<IPlugin> plugins);

    /**
     * Calls all needed code to turn off / disable each plugin
     *
     * @param plugins list of activated plugins
     * @return list of plugins that turned off correctly
     */
    List<IPlugin> stopPlugins(List<IPlugin> plugins);

    /**
     * Based on the command­line parameter for the name of the persistence plugin and
     * number of commands, N, between updates of the game state blobs
     * <p>
     * Takes PluginConfig object from params from the cmd line args
     * Future implementation could also handel a config file for several plugin cmd options
     * String must be in the format of name:option, name2:option
     * Example - mongo:100
     *
     * @param s String config
     * @return list of pluginConfigs
     */
    List<PluginConfig> parseConfig(String s);
}
