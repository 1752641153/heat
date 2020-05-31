package com.itwanli.heat.opencv.config;

import com.itwanli.heat.opencv.utils.NumbersUtils;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class DetectFaceDemo {

    public void go(String srcFileName,String newPath) {

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
            double average = 0 ;
            int c = 0;
            for (int x=0;x<3050;x=x+20){
                for(int y = 0;y<1250;y=y+20) {
                    double num = NumbersUtils.getTemperature();
                    if (rect.x < x && x < (rect.x + rect.width) && rect.y < y && y < (rect.y + rect.height))
                    {
                        System.out.println("("+(rect.x*2+rect.width)/2+","+(rect.y*2+rect.height)/2+")--" + num);
                        c++;
                        average = average + num;
                    }
                }
            }
            System.out.println("平均温度："+ average/c);
        }

//         Save the visualized detection.
        System.out.println(String.format("Writing %s", newPath));
        Imgcodecs.imwrite(newPath, image);
    }


}
