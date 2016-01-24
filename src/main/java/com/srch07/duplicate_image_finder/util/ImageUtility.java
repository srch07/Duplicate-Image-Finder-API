package com.srch07.duplicate_image_finder.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by anandabh on 1/24/2016.
 */
public class ImageUtility {
    public static BufferedImage resizeAndGreyImage(BufferedImage image, int width, int height){

        //int type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
        //BufferedImage resizedImage = new BufferedImage(width, height, type);

        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = resizedImage.createGraphics();
        graphics.drawImage(image, 0, 0, width, height, null);
        graphics.dispose();
        graphics.setComposite(AlphaComposite.Src);

        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;
    }

    private static BufferedImage greyImage(BufferedImage image){
        BufferedImage greyImage =
                new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = greyImage.createGraphics();
        graphics.drawImage(image,0,0,null);
        return greyImage;
    }

    public static int[][] getGreyPixelArray(BufferedImage image){
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] greyPixelArray = new int[height][width];
        for(int y=0;y<image.getHeight();y++)
            for(int x=0; x<image.getWidth(); x++)
                greyPixelArray[y][x] = image.getRGB(x, y)& 0xFF;
        return  greyPixelArray;
    }

    public static String generateImageHash(int[][] colorArray){
        StringBuffer sb = new StringBuffer();
        int height = colorArray.length;
        int width = colorArray[0].length - 1;
        for(int y=0; y< width; y++) {
            for (int x = 0; x <  height; x++) {
                sb.append(colorArray[y][x] < colorArray[y][x+1]?"1":"0");
            }
        }
        return sb.toString();
    }
    public static String getImageHash(String filePath, Map<String, Integer> imageAtrributes ){
        String returnValue = null;

        try{
            returnValue = generateImageHash(
                 getGreyPixelArray(
                        resizeAndGreyImage(
                                ReadImage.readFromPath(filePath, imageAtrributes), 9, 8
                        )
                )
            );
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnValue;
    }
    public static String getImageHash(File filePath, Map<String, Integer> imageAtrributes ){
        String returnValue = null;

        try{
            returnValue = generateImageHash(
                    getGreyPixelArray(
                            resizeAndGreyImage(
                                    ReadImage.readFromPath(filePath, imageAtrributes), 9, 8
                            )
                    )
            );
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnValue;
    }
}
