package server.plugin;

import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static server.plugin.PluginConfig.PluginType.PERSISTENCE;

public class PluginLoader implements IPluginLoader {

    /**
     * @inheritDoc
     */
    @Override
    public List<IPlugin> loadConfig(List<PluginConfig> pluginConfigs, File folder) {
        // neededPlugins
        List<String> neededPlugins = pluginConfigs.stream()
                .map(PluginConfig::getJarName)
                .collect(Collectors.toList());

        // zipNameConfigs
        Map<String, Map> zipNameConfigs = pluginConfigs.stream()
                .collect(Collectors.toMap(PluginConfig::getJarName, PluginConfig::getOtherArgs));

        // loadConfig
        return Arrays.stream(folder.listFiles())
                .filter(x -> neededPlugins.contains(x.getName()))
                .map(x -> toClass(x, zipNameConfigs.get(x.getName())))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    /**
     * @inheritDoc
     */
    @Override
    public List<IPlugin> startPlugins(List<IPlugin> plugins) {
        return plugins.stream()
                .map(IPlugin::start)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<IPlugin> stopPlugins(List<IPlugin> plugins) {
        return plugins.stream()
                .map(IPlugin::stop)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<PluginConfig> parseConfig(String configPath) {
        try {
            YamlReader reader = new YamlReader(new FileReader(configPath));
            Map config = (Map) reader.read();
            List<Map> availablePlugins = (List) config.get("available_plugins");

            return availablePlugins.stream()
                    .map(x -> new PluginConfig(
                            (String) ((Map) x.get("plugin")).get("name"),
                            (String) ((Map) x.get("plugin")).get("jarName"),
                            PERSISTENCE,
                            (Map) ((Map) x.get("plugin")).getOrDefault("params", null)))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Where should we look for the class we are trying to instantiate
     *
     * @param f location of class
     * @return IPlugin
     */
    private IPlugin toClass(File f, Map config) {
        try {
            URL url;
            url = f.toURL();
            URL[] urls = new URL[]{url};
            ClassLoader cl = new URLClassLoader(urls);
            Class<?> c;

            Class[] cArg = new Class[1];
            Class<IPlugin> classToLoad = IPlugin.class;
            IPlugin c_;
            c_ = classToLoad.getDeclaredConstructor(cArg).newInstance(config);

            return c_;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
