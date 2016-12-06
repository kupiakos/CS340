package server.plugin;

import server.plugin.PluginConfig.PluginType;

import java.net.URLClassLoader;
import java.util.Map;

/**
 * Plugins are bundled into JAR files
 */
public abstract class AbstractPlugin {
    /**
     * Any needed config to start the AbstractPlugin
     * @param config
     */
    public AbstractPlugin(Map config) {
    }

    /**
     * Calls any setup and init needed for a generic plugin.
     *
     * @return itself as a started plugin, else null on error
     */
    AbstractPlugin start() {
        return null;
    }

    /**
     * Calls any tear down needed for a specfic plugin
     *
     * @return itself as a stopped plugin, else null or error
     */
    AbstractPlugin stop() {
        return null;
    }

    /**
     * Type - ie persistence or other if we wanted to
     */
    public PluginType getType() {
        return null;
    }

    /**
     * Name - ie mongo or postgres... must match config file name
     */
    public PluginType getName() {
        return null;
    }

    /**
     * URLClassLoader - needed for init
     * I think it will be best to package the class in .jar file
     * see: http://stackoverflow.com/questions/738393/how-to-use-urlclassloader-to-load-a-class-file
     *
     * Do the following:
     * URLClassLoader urlClassLoader = new URLClassLoader(new URL(yourClassUrls));
     */
    URLClassLoader getURLClassLoader() {
        return null;
    }
}
