package server.plugin;


import java.util.ArrayList;

public class PluginLoader implements IPluginLoader {
    @Override
    public ArrayList<IPlugin> loadConfig(ArrayList<PluginConfig> configs) {
        return null;
    }

    @Override
    public ArrayList<IPlugin> startPlugins(ArrayList<IPlugin> plugins) {
        return null;
    }

    @Override
    public ArrayList<IPlugin> stopPlugins(ArrayList<IPlugin> plugins) {
        return null;
    }

    @Override
    public ArrayList<PluginConfig> parseConfig(Object o) {
        return null;
    }
}
