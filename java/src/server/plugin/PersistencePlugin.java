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
        return PluginConfig.PluginType.PERSISTENCE;
    }

    @Override
    public abstract String getName();

    @Override
    public URLClassLoader getURLClassLoader() {
        return null;
    }
}
