package server.plugin;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class PluginLoader implements IPluginLoader {

    /**
     * @inheritDoc
     */
    @Override
    public List<IPlugin> loadConfig(List<PluginConfig> configs, File folder) {
        List<String> neededPlugins = configs.stream()
                .map(o -> o.getName().toLowerCase())
                .collect(Collectors.toList());

        return Arrays.stream(folder.listFiles())
                .filter(x -> x.getName().length() >= 4)
                .filter(x -> ".jar".equals(x.getName().substring(x.getName().length() - 4, x.getName().length())))
                .filter(x -> neededPlugins.contains(x.getName().substring(0, x.getName().length() - 4).toLowerCase()))
                .map(this::toClass)
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
    public List<PluginConfig> parseConfig(String config) {
        return stream(config.split(","))
                .map(c -> c.split(":"))
                .filter(x -> x.length >= 2)
                .map(cs -> new PluginConfig(cs[0], Integer.parseInt(cs[1])))
                .collect(Collectors.toList());
    }

    private IPlugin toClass(File f) {
        URL url = null;
        try {
            url = f.toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URL[] urls = new URL[]{url};
        ClassLoader cl = new URLClassLoader(urls);

        Class<?> c = null;
        try {
            c = cl.loadClass("Plugin");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        IPlugin c_ = null;
        try {
            c_ = (IPlugin) c.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return c_;
    }
}
