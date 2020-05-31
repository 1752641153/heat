package com.itwanli.heat.opencv.camera;

import com.itwanli.heat.opencv.config.DetectFaceDemo;
import com.itwanli.heat.opencv.utils.MainExam;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.opencv.core.Core;

import javax.swing.*;

public class JavavcCameraTest {

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
            new MainExam(ex);
            new DetectFaceDemo().go("d:\\img\\105.jpg","D:\\img\\"+ex+".jpg");


        }
    }

}
