package server.plugin;

import server.plugin.PluginConfig.PluginType;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Plugins are bundled into JAR files
 */
public interface IPlugin {
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
     * URLClassLoader - needed for init
     * I think it will be best to package the class in .jar file
     * see: http://stackoverflow.com/questions/738393/how-to-use-urlclassloader-to-load-a-class-file
     *
     * Do the following:
     * URLClassLoader urlClassLoader = new URLClassLoader(new URL(yourClassUrls));
     */
    URLClassLoader getURLClassLoader();
}
