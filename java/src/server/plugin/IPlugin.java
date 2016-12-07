package server.plugin;

import server.plugin.PluginConfig.PluginType;

import java.net.URLClassLoader;
import java.util.Map;

/**
 * Plugins are bundled into JAR files
 */
public interface IPlugin {
    /**
     * Any needed config options for when we start up
     */
    Map configOptions = null;

    /**
     * Calls any setup and init needed for a generic plugin.
     *
     * @return itself as a started plugin, else null on error
     */
    IPlugin start();

    /**
     * Calls any tear down needed for a specfic plugin
     *
     * @return itself as a stopped plugin, else null or error
     */
    IPlugin stop();

    /**
     * Type - ie persistence or other if we wanted to
     */
    PluginType getType();

    /**
     * Name - ie mongo or postgres... must match config file name
     */
    String getName();

    /**
     * URLClassLoader - needed for init
     * I think it will be best to package the class in .jar file
     * see: http://stackoverflow.com/questions/738393/how-to-use-urlclassloader-to-load-a-class-file
     * <p>
     * Do the following:
     * URLClassLoader urlClassLoader = new URLClassLoader(new URL(yourClassUrls));
     */
    URLClassLoader getURLClassLoader();
}
