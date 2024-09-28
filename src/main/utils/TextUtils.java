package main.utils;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TextUtils {
    public static Font getFont(int size) {
        try {
            // Create the font to use. Specify the size!
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/fonts/FONT.ttf")).deriveFont((float) size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            // Register the font
            ge.registerFont(customFont);
            return customFont;
        } catch (IOException | FontFormatException e) {
            System.err.println("Error loading font: " + e.getMessage());
            // Fallback to a default font in case of error
            return new Font("Arial", Font.PLAIN, size);
        }
    }
}