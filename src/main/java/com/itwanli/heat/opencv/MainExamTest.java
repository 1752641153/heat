package com.itwanli.heat.opencv;

import com.itwanli.heat.opencv.config.DetectFaceDemo;
import com.itwanli.heat.opencv.utils.NumbersUtils;
import lombok.SneakyThrows;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.opencv.core.Point;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainExamTest extends JFrame {

    private MyLabel backgroundLabel = null;

    static OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();

    public static void main(String[] args) throws Exception, InterruptedException
    {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.start();   //开始获取摄像头数据
        CanvasFrame canvas = new CanvasFrame("摄像头");//新建一个窗口
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setAlwaysOnTop(true);
        int ex = 0;
        while(true)
        {
            if(!canvas.isDisplayable())
            {//窗口是否关闭
                grabber.stop();//停止抓取
                System.exit(2);//退出
                break;
            }
            canvas.showImage(grabber.grab());//获取摄像头图像并放到窗口上显示， 这里的Frame frame=grabber.grab(); frame是一帧视频图像
            opencv_core.Mat mat = converter.convertToMat(grabber.grabFrame());
            ex++;
            opencv_imgcodecs.imwrite("d:\\img\\" + ex + ".jpg", mat);
            Thread.sleep(200);//50毫秒刷新一次图像
            new MainExamTest();
            go("d:\\img\\105.jpg","D:\\img\\"+ex+".jpg");


        }
    }

    public static void go(String srcFileName, String newPath) {

        Mat image = null;
        CascadeClassifier faceDetector = null;
        System.out.println(DetectFaceDemo.class.getClassLoader().getResource("lbpcascade_frontalface.xml").getPath().substring(1));
        String xmlfilePath = DetectFaceDemo.class.getClassLoader().getResource("lbpcascade_frontalface.xml").getPath().substring(1);
        //String xmlfilePath = DetectFaceDemo.class.getClassLoader().getResource("D:\\G-YY\\IdeaProjects\\springboot\\heat\\src\\lbpcascade_frontalface.xml").getPath().substring(1);
        try {
            faceDetector = new CascadeClassifier(xmlfilePath);
            image = Imgcodecs.imread(srcFileName);
        }catch (Exception e){
            e.printStackTrace();
        }
        // Detect faces in the image.
        // MatOfRect is a special container class for Rect.
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));


        // Draw a bounding box around each face.
        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
            for (int x=0;x<3050;x=x+50){
                for(int y = 0;y<1250;y=y+50) {
                    double num = NumbersUtils.getTemperature();
                    if (rect.x < x && x < (rect.x + rect.width) && rect.y < y && y < (rect.y + rect.height))
                    {
                        System.out.println(num);
                    }
                }
            }
        }

//         Save the visualized detection.
        System.out.println(String.format("Writing %s", newPath));
        Imgcodecs.imwrite(newPath, image);
    }

    public MainExamTest() {
        backgroundLabel = new MyLabel();

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, (int) size.getWidth(), (int) size.getHeight());

        setUndecorated(true);
        setOpacity(1.0f);
        add(backgroundLabel);
        setVisible(true);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }

    public class MyLabel extends JLabel {

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
                backImage = ImageIO.read(new File("D:\\img\\" + ex+".jpg"));
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
