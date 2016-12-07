package server.plugin;

import java.net.URLClassLoader;
import java.util.Map;

public class PersistencePlugin implements IPlugin {
    public PersistencePlugin(Map configs) {
    }

    @Override
    public IPlugin start() {
        return null;
    }

    @Override
    public IPlugin stop() {
        return null;
    }

    @Override
    public PluginConfig.PluginType getType() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public URLClassLoader getURLClassLoader() {
        return null;
    }
}
