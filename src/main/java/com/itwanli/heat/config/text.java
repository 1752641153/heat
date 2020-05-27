package com.itwanli.heat.config;

import org.aspectj.weaver.ast.Test;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class text {

    //引入训练好的人脸识别XML文件
    static String PAHT = "D:\\G-YY\\IdeaProjects\\springboot\\heat\\src\\haarcascade_frontalface_alt.xml";
    static String IMAGE_PATH = "D:/group2.jpg";
    static String productPath = "E:/GOF/OpenCV";

    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String Path = Test.class.getClassLoader().getResource("haarcascade_frontalface_alt.xml").getPath();
        System.out.println(Path);
        CascadeClassifier faceDetector = new CascadeClassifier(PAHT);
        Mat image = Imgcodecs.imread(IMAGE_PATH);
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
        }

        String filename = "ouput.png";
        System.out.println(String.format("Writing %s", filename));
        boolean flag = Imgcodecs.imwrite(filename, image);
    }
}
