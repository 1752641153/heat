package com.itwanli.heat.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ImageRemarkUtil {

    // 水印透明度
    private static float alpha = 1f;
    // 水印横向位置
    private static int positionWidth = 0;
    // 水印纵向位置
    private static int positionHeight = 0;

    /**
     *
     * @param alpha
     *            水印透明度
     * @param positionWidth
     *            水印横向位置
     * @param positionHeight
     *            水印纵向位置
     */
    public static void setImageMarkOptions(float alpha, int positionWidth,
                                           int positionHeight, Font font, Color color) {
        if (alpha != 0.0f)
            ImageRemarkUtil.alpha = alpha;
        if (positionWidth != 0)
            ImageRemarkUtil.positionWidth = positionWidth;
        if (positionHeight != 0)
            ImageRemarkUtil.positionHeight = positionHeight;
    }

    /*
     * 给图片添加水印图片
     *
     * @param iconPath
     *            水印图片路径
     * @param srcImgPath
     *            源图片路径
     * @param targerPath
     *            目标图片路径
     * @param height
     * @param width
     */
//	public static void markImageByIcon(String iconPath, String srcImgPath,
//			String targerPath,String watermarkPosition,int width, int height) {
//		markImageByIcon(iconPath, srcImgPath, targerPath,watermarkPosition,width, height);
//	}

    public static void main(String[] args) {
        markImageByIcon("D:\\group10.jpg","D:\\11.jpg","D:\\group01.jpg",0,"bottomRight",0.9f,0,0);
    }
    /**
     * 给图片添加水印图片、可设置水印图片旋转角度
     *
     * @param iconPath
     *            水印图片路径
     * @param srcImgPath
     *            源图片路径
     * @param targerPath
     *            目标图片路径
     * @param degree
     *            水印图片旋转角度
     * @param markAlpha
     */
    public static void markImageByIcon(String iconPath, String srcImgPath, String targerPath, Integer degree, String watermarkPosition,float markAlpha, int width, int height) {
        OutputStream os = null;
        try {

            Image srcImg = ImageIO.read(new File(srcImgPath));

            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
            int srcWidth=srcImg.getWidth(null);
            int srcHeight=srcImg.getHeight(null);
            // 1、得到画笔对象
            Graphics2D g = buffImg.createGraphics();

            // 2、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            g.drawImage(
                    srcImg.getScaledInstance(srcImg.getWidth(null),
                            srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0,null);

            // 3、设置水印旋转
            if (null != degree) {
                g.rotate(Math.toRadians(degree),
                        (double) buffImg.getWidth() / 2,
                        (double) buffImg.getHeight() / 2);
            }
            // 4、水印图片的路径 水印图片一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(iconPath);
            // 5、得到Image对象。
            Image img = imgIcon.getImage();
            int watermarkImageWidth=img.getWidth(null);
            int watermarkImageHeight=img.getHeight(null);
            double dmarkWidth = width*0.4;// 设置水印的宽度为图片宽度的0.4倍
            double dmarkHeight = dmarkWidth * ((double)watermarkImageHeight/(double)watermarkImageWidth);//强转为double计算宽高比例
            int imarkWidth = (int) dmarkWidth;
            int imarkHeight = (int) dmarkHeight;
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,	markAlpha));
            // 6、水印图片的位置
            int x;
            int y;
            switch (watermarkPosition) {
                case "topLeft":
                    x = 0;
                    y = 0;
                    break;
                case "topRight":
                    x = srcWidth - imarkWidth;
                    y = 0;
                    break;
                case "topCenter":
                    x = (srcWidth - imarkWidth) / 2;
                    y = 0;
                    break;
                case "center":
                    x = (srcWidth - imarkWidth) / 2;
                    y = (srcHeight - imarkHeight) / 2;
                    break;
                case "centerLeft":
                    x = 0;
                    y = (srcHeight - imarkHeight) / 2;
                    break;
                case "centerRight":
                    x = srcWidth - imarkWidth;
                    y = (srcHeight - imarkHeight) / 2;
                    break;
                case "bottomLeft":
                    x = 0;
                    y = srcHeight - imarkHeight;
                    break;
                case "bottomCenter":
                    x = (srcWidth - imarkWidth) / 2;
                    y = srcHeight - imarkHeight;
                    break;
                case "bottomRight":
                    x = srcWidth - imarkWidth;
                    y = srcHeight - imarkHeight;
                    break;
                default:
                    x = srcWidth - imarkWidth;
                    y = srcHeight - imarkHeight;
                    break;
            }

//			double dmarkWidth = width*0.4;// 设置水印的宽度为图片宽度的0.4倍
//			double dmarkHeight = dmarkWidth * ((double)watermarkImageHeight/(double)watermarkImageWidth);//强转为double计算宽高比例
//			int imarkWidth = (int) dmarkWidth;
//			int imarkHeight = (int) dmarkHeight;
            //positionWidth = (int) (width - dmarkWidth);
            //	positionHeight = (int) (height - dmarkHeight);



            g.drawImage(img, x, y, imarkWidth,	imarkHeight, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            // 7、释放资源
            g.dispose();
            // 8、生成图片
            os = new FileOutputStream(targerPath);
            ImageIO.write(buffImg, "JPG", os);
            System.out.println("图片完成添加水印图片");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
