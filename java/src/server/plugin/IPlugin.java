package server.plugin;

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
}
