package com.srch07.duplicate_image_finder.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by anandabh on 1/24/2016.
 */
public class ReadImage {
    private ReadImage(){
    }
    public static BufferedImage readFromPath(String filePath, Map<String, Integer> imageAtrributes) throws IOException{
        try{
            BufferedImage loadImage = ImageIO.read(new File(filePath));
            imageAtrributes.put(Constants.IMAGE_WIDTH, loadImage.getWidth());
            imageAtrributes.put(Constants.IMAGE_HEIGHT, loadImage.getHeight());
            return loadImage;
        } catch (IOException ioe){
            ioe.printStackTrace();
            throw ioe;
        }
    }
    public static BufferedImage readFromPath(File filePath, Map<String, Integer> imageAtrributes) throws IOException{
        try{
            BufferedImage loadImage = ImageIO.read(filePath);
            imageAtrributes.put(Constants.IMAGE_WIDTH, loadImage.getWidth());
            imageAtrributes.put(Constants.IMAGE_HEIGHT, loadImage.getHeight());
            return loadImage;
        } catch (IOException ioe){
            ioe.printStackTrace();
            throw ioe;
        }
    }
}
