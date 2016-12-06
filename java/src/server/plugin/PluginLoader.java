package server.plugin;

import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.stream.Collectors;

import static server.plugin.PluginConfig.PluginType.PERSISTENCE;

public class PluginLoader implements IPluginLoader {

    /**
     * @inheritDoc
     */
    @Override
    public List<AbstractPlugin> loadConfig(List<PluginConfig> pluginConfigs, File folder) {
        // neededPlugins
        List<String> neededPlugins = pluginConfigs.stream()
                .map(PluginConfig::getJarName)
                .collect(Collectors.toList());

        // neededConfigs
        List<Map> neededConfigs = pluginConfigs.stream()
                .map(PluginConfig::getOtherArgs)
                .collect(Collectors.toList());

        // zipNameConfigs
        Map<String, Map> zipNameConfigs = zipNameConfig(neededPlugins, neededConfigs);

        // loadConfig
        return Arrays.stream(folder.listFiles())
                .filter(x -> neededPlugins.contains(x.getName()))
                .map(x -> toClass(x, zipNameConfigs.get(x.getName())))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    private Map<String, Map> zipNameConfig(List<String> neededPlugins, List<Map> neededConfigs) {
        Iterator<String> i1 = neededPlugins.iterator();
        Iterator<Map> i2 = neededConfigs.iterator();
        Map<String, Map> zipNameConfigs = new HashMap<>();
        while (i1.hasNext() && i2.hasNext()) {
            zipNameConfigs.put(i1.next(), i2.next());
        }
        return zipNameConfigs;
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<AbstractPlugin> startPlugins(List<AbstractPlugin> plugins) {
        return plugins.stream()
                .map(AbstractPlugin::start)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<AbstractPlugin> stopPlugins(List<AbstractPlugin> plugins) {
        return plugins.stream()
                .map(AbstractPlugin::stop)
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
                            (Map) ((Map) x.get("plugin")).getOrDefault("other_params", null)))
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
     * @return AbstractPlugin
     */
    private AbstractPlugin toClass(File f, Map config) {
        try {
            URL url;
            url = f.toURL();
            URL[] urls = new URL[]{url};
            ClassLoader cl = new URLClassLoader(urls);
            Class<?> c;

            Class[] cArg = new Class[1];
            Class<AbstractPlugin> classToLoad = AbstractPlugin.class;
            AbstractPlugin c_;
            c_ = classToLoad.getDeclaredConstructor(cArg).newInstance(config);

            return c_;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
