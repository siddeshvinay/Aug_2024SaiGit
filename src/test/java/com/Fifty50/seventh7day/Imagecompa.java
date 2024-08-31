package com.Fifty50.seventh7day;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.apache.commons.imaging.ImageCompare;
/*import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;*/

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Imagecompa {
    public WebDriver driver;

    public void imagecomp(){
        File soure= new File("patheof the .png");
        File target= new File("trarget path .png");

        //ImageCompare compa= new ImageCompare(source, traget);
        //boolean status=compa.compare();

    }

    public void imgatecom(){
/*        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat expectedImage = Imgcodecs.imread("expected_image.png");
        Mat actualImage = Imgcodecs.imread("actual_image.png");

        Mat diff = new Mat();
        Core.absdiff(expectedImage, actualImage, diff);

        double similarity = Imgproc.countNonZero(diff) / (expectedImage.rows() * expectedImage.cols());

        if (similarity < 0.1) {
            System.out.println("Images are equal");
        } else {
            System.out.println("Images are not equal");
        }
    }*/
    }

    public void imagea() throws IOException {
        File expectedImageFile = new File("expected_image.png");
        File actualImageFile = new File("actual_image.png");

        BufferedImage expectedImage;
        BufferedImage actualImage;
        expectedImage = ImageIO.read(expectedImageFile);
        actualImage = ImageIO.read(actualImageFile);

        int width = expectedImage.getWidth();
        int height = expectedImage.getHeight();

        boolean imagesAreEqual = true;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int expectedPixel = expectedImage.getRGB(x, y);
                int actualPixel = actualImage.getRGB(x, y);

                if (expectedPixel != actualPixel) {
                    imagesAreEqual = false;
                    break;
                }
            }
        }

        if (imagesAreEqual) {
            System.out.println("Images are equal");
        } else {
            System.out.println("Images are not equal");
        }


    }

    public void openCV(String src, String targe){

    }
}
