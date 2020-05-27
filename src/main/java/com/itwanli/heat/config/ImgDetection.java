package com.itwanli.heat.config;

import org.opencv.core.Core;

public class ImgDetection {

    public static void main(String[] args) {
        // Load the native library.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String url ="D:\\group1.jpg";
        new DetectFaceDemo().go(url,"D:\\10.jpg");
    }
}
