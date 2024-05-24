package com.szalay.thinktools.ui;

import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

public final class TextUtil {
    public static String getText(final String key) {
        Objects.requireNonNull(key);

        try {
            final ResourceBundle bundle = ResourceBundle.getBundle("Texts");
            return bundle.getString(key);
        }
        catch (MissingResourceException mre) {
            return "###" + key + "###";
        }
    }
}