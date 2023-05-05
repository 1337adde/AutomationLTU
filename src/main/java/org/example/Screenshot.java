package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Screenshot {
    public static void take(String fileName) throws Exception {
        // Create a Robot instance
        Robot robot = new Robot();

        // Get the screen size
        Rectangle screenSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

        // Capture the screen using the Robot class
        BufferedImage screenImage = robot.createScreenCapture(screenSize);

        // Save the captured screenshot as a JPEG file
        File file = new File("build//downloads//" + fileName + ".jpeg");
        ImageIO.write(screenImage, "jpeg", file);
    }
}
