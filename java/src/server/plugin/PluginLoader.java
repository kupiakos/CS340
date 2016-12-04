package server.plugin;

import java.util.List;
import java.util.stream.Collectors;

public class PluginLoader implements IPluginLoader {

    /**
     * @inheritDoc
     */
    @Override
    public List<IPlugin> loadConfig(List<PluginConfig> configs) {
        return null;
    }

    @Override
    public List<IPlugin> startPlugins(List<IPlugin> plugins) {
        return plugins.stream()
                .map(IPlugin::start)
                .collect(Collectors.toList());
    }

    @Override
    public List<IPlugin> stopPlugins(List<IPlugin> plugins) {
        return plugins.stream()
                .map(IPlugin::stop)
                .collect(Collectors.toList());
    }

    @Override
    public List<PluginConfig> parseConfig(Object o) {
        return null;
    }
}
