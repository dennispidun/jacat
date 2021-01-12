package de.unihildesheim.sse.jacat.platform.addon;

import de.unihildesheim.sse.jacat.platform.JacatPlatform;
import lombok.SneakyThrows;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class AddonClassLoader extends URLClassLoader {
    private final File addonJarFile;

    public AddonClassLoader(final File addonJarFile, AddonDescription addonDescription, JacatPlatform jacatPlatform) throws MalformedURLException {
        super(new URL[] {addonJarFile.toURI().toURL()}, AddonClassLoader.class.getClassLoader());
        this.addonJarFile = addonJarFile;


        try {
            Class<?> jarClass = Class.forName(addonDescription.getMainClass(), true, this);
            Object addon = jarClass.getDeclaredConstructor().newInstance();

            Field platform = addon.getClass().getSuperclass().getDeclaredField("platform");
            platform.setAccessible(true);
            platform.set(addon, jacatPlatform);

            ((JavaAddon) addon).onEnable();

        } catch (InstantiationException
                | InvocationTargetException
                | IllegalAccessException
                | ClassNotFoundException
                | NoSuchMethodException
                | NoSuchFieldException e) {
            throw new RuntimeException("Could not load addon", e);
        }
        System.out.println("Addon loaded");

    }
}
