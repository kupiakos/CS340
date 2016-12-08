package server.plugin;

import server.db.IPersistenceProvider;

import java.net.URLClassLoader;
import java.util.Map;

public abstract class PersistencePlugin implements IPersistenceProvider {
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
