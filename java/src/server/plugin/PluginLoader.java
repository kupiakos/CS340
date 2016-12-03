package server.plugin;

import java.util.List;

public class PluginLoader implements IPluginLoader {


    @Override
    public List<IPlugin> loadConfig(List<PluginConfig> configs) {
        return null;
    }

    @Override
    public List<IPlugin> startPlugins(List<IPlugin> plugins) {
        return null;
    }

    @Override
    public List<IPlugin> stopPlugins(List<IPlugin> plugins) {
        return null;
    }

    @Override
    public List<PluginConfig> parseConfig(Object o) {
        return null;
    }
}
