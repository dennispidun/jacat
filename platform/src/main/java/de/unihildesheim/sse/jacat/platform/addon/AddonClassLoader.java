package de.unihildesheim.sse.jacat.platform.addon;

import de.unihildesheim.sse.jacat.addon.Addon;
import de.unihildesheim.sse.jacat.addon.AddonDescription;
import de.unihildesheim.sse.jacat.platform.JacatPlatform;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class AddonClassLoader extends URLClassLoader {
    public static final String PLATFORM_FIELD_NAME = "platform";
    public static final String DESCRIPTION_FIELD_NAME = "description";

    private final File addonJarFile;
    private Addon addon;

    public AddonClassLoader(final File addonJarFile, AddonDescription addonDescription, JacatPlatform jacatPlatform) throws MalformedURLException {
        super(new URL[] {addonJarFile.toURI().toURL()}, AddonClassLoader.class.getClassLoader());
        this.addonJarFile = addonJarFile;

        try {
            Class<?> jarClass = Class.forName(addonDescription.getMainClass(), true, this);
            Object addon = jarClass.getDeclaredConstructor().newInstance();

            setCustomValue(addon, PLATFORM_FIELD_NAME, jacatPlatform);
            setCustomValue(addon, DESCRIPTION_FIELD_NAME, addonDescription);

            ((Addon) addon).onEnable();

            this.addon = (Addon) addon;

        } catch (InstantiationException
                | InvocationTargetException
                | IllegalAccessException
                | ClassNotFoundException
                | NoSuchMethodException
                | NoSuchFieldException e) {
            throw new AddonNotLoadableException(e);
        }
        System.out.println("Addon loaded");
    }

    public Addon getLoadedAddon() {
        return this.addon;
    }



    private void setCustomValue(Object addon, String declaredField, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field platform = addon.getClass().getSuperclass().getDeclaredField(declaredField);
        platform.setAccessible(true);
        platform.set(addon, value);
    }
}
