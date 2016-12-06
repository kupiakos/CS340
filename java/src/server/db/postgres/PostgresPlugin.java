package server.db.postgres;

import server.plugin.IPlugin;
import server.plugin.PluginConfig;

import java.net.URLClassLoader;

/**
 * Created by elija on 12/6/2016.
 */
public class PostgresPlugin implements IPlugin {
    PostgresProvider provider;

    public PostgresPlugin(String username, String password) {
        provider = new PostgresProvider(username, password);
    }

    @Override
    public IPlugin start() {
        provider.createDB();
        return this;
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
    public URLClassLoader getURLClassLoader() {
        return null;
    }

    public PostgresProvider getProvider() {
        return provider;
    }
}
