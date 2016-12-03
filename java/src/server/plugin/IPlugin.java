package server.plugin;

/**
 * Plugins are bundled into JAR files
 */
public interface IPlugin {
    /**
     * Calls any setup and init needed for a generic plugin
     */
    boolean start();

    /**
     * Calls any tear down needed for a specfic plugin
     */
    boolean stop();
}
