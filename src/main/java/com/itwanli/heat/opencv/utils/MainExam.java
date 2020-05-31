package com.itwanli.heat.opencv.utils;

import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainExam extends JFrame {


    private int PIC_NUM = 0;
    private String[] listFile = null;
    private final String FILE_PATH = "D:\\img\\";
    private MyLabel backgroundLabel = null;


    public MainExam(int ex) {
        backgroundLabel = new MyLabel(ex);

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, (int) size.getWidth(), (int) size.getHeight());

        setUndecorated(true);
        setOpacity(1.0f);
        add(backgroundLabel);
        setVisible(true);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }

    public static void main(String[] args) {

        new MainExam(39);
    }


    public class MyLabel extends JLabel {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private Image backImage;

        public MyLabel() {
            try {
                backImage = ImageIO.read(new File("D:\\img\\" +"213.jpg"));
            } catch (IOException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        public MyLabel(int ex) {
            try {
                backImage = ImageIO.read(new File(FILE_PATH + ex+".jpg"));
            } catch (IOException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @SneakyThrows
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int width = this.getWidth();
            int height = this.getHeight();
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D ig2 = bi.createGraphics();
            ig2.drawImage(backImage, 0, 0, width, height, this);
            //在此处实现文字写入
            for (int x=0;x<3050;x=x+50){
                for(int y = 0;y<1250;y=y+50) {
                    double num = NumbersUtils.getTemperature();
                    if (num < 37.3){
                        ig2.setColor(Color.green);
                        ig2.drawString(String.valueOf(num), x, y);
                    }else {
                        ig2.setColor(Color.red);
                        ig2.drawString(String.valueOf(num), x, y);
                    }
                }
            }
            int a=105;
            ImageIO.write(bi, "JPEG", new File("D:\\img\\"+a+".JPEG"));
            ImageIO.write(bi, "PNG", new File("D:\\img\\"+a+".PNG"));
        }

    }


}

