package de.unihildesheim.sse.jacatplatform.addon;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class AddonClassLoader extends URLClassLoader {
    public AddonClassLoader(final ClassLoader parent, final File file) throws MalformedURLException {
        super(new URL[] {file.toURI().toURL()}, parent);

        try {
            Class<?> jarClass = Class.forName("me.dennis.test.Main", true, this);
            Object obj = jarClass.getDeclaredConstructor().newInstance();
            Method enable = jarClass.getMethod("enable");
            enable.invoke(obj);

        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException("Could not load addon", e);
        }
        System.out.println("Addon loaded");

    }
}
