package server.plugin;

import com.esotericsoftware.yamlbeans.YamlReader;
import server.db.mongodb.MongoProvider;
import server.db.postgres.PostgresProvider;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
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
            //TODO dynamically find class to construct from config

            IPlugin result = null;
            if (config.get("name").equals("postgres")) {
                Class[] cArg = new Class[]{Map.class};
                Class<PostgresProvider> classToLoad = PostgresProvider.class;
                result = classToLoad.getDeclaredConstructor(cArg).newInstance(config);
            } else if (config.get("name").equals("mongo")) {
                Class[] cArg = new Class[]{Map.class};
                Class<MongoProvider> classToLoad = MongoProvider.class;
                result = classToLoad.getDeclaredConstructor(cArg).newInstance(config);
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
