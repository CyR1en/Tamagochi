package com.cyr1en.cgdl.util;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class FontUtil {

    private static Map<String, Font> cache = new ConcurrentHashMap<>();

    public static Font getFont(String fileName) {
        Font font;
        String name = fileName.substring(fileName.lastIndexOf('/') + 1);
        System.out.println(name);
        if (cache != null) {
            if (cache.containsKey(name)) {
                return cache.get(name);
            }
        }
        try {
            InputStream is = FontUtil.class.getResourceAsStream(fileName);
            font = Font.createFont(Font.TRUETYPE_FONT, is);

            cache.put(name, font);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println(fileName + " not loaded.  Using serif font.");
            font = new Font("serif", Font.PLAIN, 24);
        }
        return font;
    }

    public static void registerFont(String fileName) {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            InputStream is = FontUtil.class.getResourceAsStream(fileName);
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, is));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void registerAllFonts(String fontsDir) {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            final File jarFile = new File(FontUtil.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            if (FileUtil.isJarFile(jarFile)) {
                if(fontsDir.startsWith("/"))
                    fontsDir = fontsDir.substring(1);
                JarFile jar = new JarFile(jarFile);
                Enumeration<JarEntry> entries = jar.entries();
                ArrayList<JarEntry> jarEntries = Collections.list(entries);
                final String finalFontsDir = fontsDir;
                List<JarEntry> fonts = jarEntries.stream()
                        .filter(f -> f.getName().startsWith(finalFontsDir) &&
                                !f.getName().equals(finalFontsDir))
                        .collect(Collectors.toList());
                fonts.forEach(f -> {
                    try {
                        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, jar.getInputStream(f)));
                    } catch (FontFormatException | IOException e) {
                        e.printStackTrace();
                    }
                });
                jar.close();
            } else {
                URL url = FontUtil.class.getResource(fontsDir);
                if (url != null) {
                    File file = new File(url.toURI());
                    if (file.listFiles() != null)
                        for (File f : file.listFiles()) {
                            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, f));
                        }
                }
            }
        } catch (IOException | FontFormatException | URISyntaxException e) {
            ExceptionUtil.generateErrorLog(e);
        }
    }


}
