package com.srch07.duplicate_image_finder.api;

import com.srch07.duplicate_image_finder.util.Constants;
import com.srch07.duplicate_image_finder.util.ImageUtility;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.*;

/**
 * Created by anandabh on 1/25/2016.
 */
public class DuplicateImageFinder {
    public static void main(String [] args){
        if(args != null && args.length >= 2){
            if("findDuplicatePairs".equalsIgnoreCase(args[0]))
                System.out.println(findDuplicatePairs(args[1]));
            else
                System.out.println("No freaking idea, what you gave in argument");
        }
        /*List<String> test = findAllUniqueImages("D:\\test\\images2\\");
        System.out.println("Size : "+test.size());
        System.out.println(test);
        *//*try{
            BufferedImage temp = ImageUtility.resizeAndGreyImage(
                    ReadImage.readFromPath("D:\\test\\images2\\image (1).jpg"), 500, 400
            );
            ImageIO.write(temp, "png", new File("D:\\test\\testing).png"));
            int[][] test = ImageUtility.getGreyPixelArray(
                    ImageUtility.resizeAndGreyImage(
                            ReadImage.readFromPath("D:\\test\\images2\\image (1).jpg"), 9, 8
                    )
            );
            System.out.println(Arrays.deepToString(test));
        }catch (Exception e){
            e.printStackTrace();
        }*/
    }
    private static boolean isSimilar(String hashA, String hashB){
        if(hashA.length() != hashB.length()){
            return false;
        }
        int distance = 0 ;
        for(int x=0;x< hashA.length();x++){
            if(hashA.charAt(x) != hashB.charAt(x))
                distance++;
        }
        if(distance <= 10)
            return true;
        else
            return false;
    }
    private static List<File> getAllImagesFromDirectory(String directoryPath){
        List<File> imagesPathList = null;
        String[] allowedExtensions = new String[] {"bmp","gif","jpeg","jpg", "png","psd","pspimage","thm","tif"};
        File file = new File(directoryPath);
        if(file.isDirectory())
            imagesPathList = (List<File>) FileUtils.listFiles(file, allowedExtensions, true);
        return imagesPathList;
    }
    private static List<Map<String, Integer>> findDuplicateImagePairs(String directoryPath){
        List<File> imagesFileList = getAllImagesFromDirectory(directoryPath);
        if(null == imagesFileList || imagesFileList.isEmpty())
            return null;
        Map<String, Integer> imagesMap= new HashMap<String, Integer>();
        Map<String, String> imagesHashMap = new LinkedHashMap<String, String>();
        Map<String, Integer> imageAttributes = new HashMap<String, Integer>();
        List<Map<String, Integer>> output = new ArrayList<Map<String, Integer>>();
        Set<String> allImagesFoundDuplicates = new HashSet<String>();
        boolean isImageFoundInPair = false;
        String imageHash = null;
        for(File imagesFile:imagesFileList){
            imageAttributes.clear();
            imageHash = null;
            imageHash = ImageUtility.getImageHash(imagesFile, imageAttributes);
            if(imageHash != null && !imageHash.isEmpty()) {
                imagesMap.put(imagesFile.getAbsolutePath(), imageAttributes.get(Constants.IMAGE_WIDTH) * imageAttributes.get(Constants.IMAGE_WIDTH));
                imagesHashMap.put(imagesFile.getAbsolutePath(), imageHash);
            }
        }
        int totalImagesCount = imagesMap.size();
        for(Map.Entry<String, String> entry1: imagesHashMap.entrySet())
            for(Map.Entry<String, String> entry2: imagesHashMap.entrySet()){
                if(!entry1.getKey().equalsIgnoreCase(entry2.getKey()) &&
                        isSimilar(entry1.getValue(), entry2.getValue())){
                    isImageFoundInPair = false;
                    for(Map outputMap: output){
                        if(outputMap.containsKey(entry1.getKey()) || outputMap.containsKey(entry2.getKey())){
                            outputMap.put(entry1.getKey(), imagesMap.get(entry1.getKey()));
                            outputMap.put(entry2.getKey(), imagesMap.get(entry2.getKey()));
                            isImageFoundInPair = true;
                            break;
                        }
                    }
                    if(!isImageFoundInPair){
                        Map<String, Integer> outputMap = new HashMap<String, Integer>();
                        outputMap.put(entry1.getKey(), imagesMap.get(entry1.getKey()));
                        outputMap.put(entry2.getKey(), imagesMap.get(entry2.getKey()));
                        output.add(outputMap);
                    }
                    allImagesFoundDuplicates.add(entry1.getKey());
                    allImagesFoundDuplicates.add(entry2.getKey());
                }
            }

        Set<String> allImages = new HashSet<String>(imagesMap.keySet());
        allImages.removeAll(allImagesFoundDuplicates);
        for(String outputElement:allImages){
            Map<String, Integer> outputMap = new HashMap<String, Integer>();
            outputMap.put(outputElement, imagesMap.get(outputElement));
            output.add(outputMap);
        }
        return output;
    }

    public static List<List<String>>findDuplicatePairs(String directoryPath){
        List<Map<String, Integer>> pairs = findDuplicateImagePairs(directoryPath);
        List<List<String>> output = new ArrayList<List<String>>();
        if(null == pairs || pairs.isEmpty())
            return null;
        for(Map<String, Integer> pair:pairs){
            if(!pair.isEmpty() && pair.keySet().size() > 1) {
                List<String> outputElement = new ArrayList<String>(pair.keySet());
                output.add(outputElement);
            }
        }
        return output;
    }
    public static List<String> findDuplicatesForDeletion(String directoryPath){
        List<Map<String, Integer>> pairs = findDuplicateImagePairs(directoryPath);
        List<String> output = new ArrayList<String>();
        boolean isFirstElementInPair = true;
        if(null == pairs || pairs.isEmpty())
            return null;
        for(Map<String, Integer> pair:pairs){
            if(!pair.isEmpty() && pair.keySet().size() > 1){
                List<Map.Entry<String, Integer>> pairEntryList =
                        new ArrayList<Map.Entry<String, Integer>>(pair.entrySet());
                Collections.sort(pairEntryList, new Comparator<Map.Entry<String, Integer>>() {
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                        return (o2.getValue()).compareTo(o1.getValue());
                    }
                });
                isFirstElementInPair = true;
                for(Map.Entry<String, Integer> entry:pairEntryList){
                    if(isFirstElementInPair){
                        isFirstElementInPair = false;
                    }else{
                        output.add(entry.getKey());
                    }
                }
            }
        }
        return output;
    }
    public static List<String> findAllUniqueImages(String directoryPath){
        List<Map<String, Integer>> pairs = findDuplicateImagePairs(directoryPath);
        List<String> output = new ArrayList<String>();
        if(null == pairs || pairs.isEmpty())
            return null;
        for(Map<String, Integer> pair:pairs){
            if(!pair.isEmpty() && pair.keySet().size() == 1) {
                output.add(String.valueOf(pair.keySet().toArray()[0]));
            }else if(!pair.isEmpty() && pair.keySet().size() > 1){
                List<Map.Entry<String, Integer>> pairEntryList =
                        new ArrayList<Map.Entry<String, Integer>>(pair.entrySet());
                Collections.sort(pairEntryList, new Comparator<Map.Entry<String, Integer>>() {
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                        return (o2.getValue()).compareTo(o1.getValue());
                    }
                });
                output.add(pairEntryList.get(0).getKey());
            }
        }
        return output;
    }
}
