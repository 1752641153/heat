package com.itwanli.heat.opencv.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WriteImageType {
    static public void main(String args[]) throws Exception {
        try {
            int width = 200, height = 200;

            // TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed
            // into integer pixels
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            Graphics2D ig2 = bi.createGraphics();


            Font font = new Font("TimesRoman", Font.BOLD, 20);
            ig2.setFont(font);
            String message = "www.w3cschool.cn!";
            FontMetrics fontMetrics = ig2.getFontMetrics();
            int stringWidth = fontMetrics.stringWidth(message);
            int stringHeight = fontMetrics.getAscent();
            ig2.setPaint(Color.black);
            ig2.drawString(message, (width - stringWidth) / 2, height / 2 + stringHeight / 4);

            ImageIO.write(bi, "PNG", new File("D:\\img\\yourImageName.PNG"));
            ImageIO.write(bi, "JPEG", new File("D:\\img\\yourImageName.JPG"));
            ImageIO.write(bi, "gif", new File("D:\\img\\yourImageName.GIF"));
            ImageIO.write(bi, "BMP", new File("D:\\img\\yourImageName.BMP"));

        } catch (IOException ie) {
            ie.printStackTrace();
        }

    }
}
