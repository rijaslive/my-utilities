package com.apps.essentials.essentials.process;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
@Slf4j
public class TesseractOCRProcess {



    @Autowired
    private ObjectFactory<Tesseract> tesseractObjectFactory;

    public Tesseract getTesseractInstance() {
        return tesseractObjectFactory.getObject();
    }


    public String recognizeText(File file, int psm) throws IOException {
        try {
            Tesseract tesseractInstance = getTesseractInstance();
            BufferedImage bufferedImage = ImageIO.read(new FileInputStream(file));
            bufferedImage = convertToGrayScale(bufferedImage);
            return tesseractInstance.doOCR(bufferedImage);
        } catch (TesseractException e) {
           log.error("Exception in OCR",e);
        }
        return null;
    }


    private BufferedImage convertToGrayScale(BufferedImage originalImage) throws IOException {
        // Load the image

        // Create a new BufferedImage with the same dimensions and grayscale type
        BufferedImage grayscaleImage = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY
        );

        // Perform the grayscale conversion
        for (int i = 0; i < originalImage.getWidth(); i++) {
            for (int j = 0; j < originalImage.getHeight(); j++) {
                Color color = new Color(originalImage.getRGB(i, j));
                int grayValue = (int) (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue());
                int grayColor = new Color(grayValue, grayValue, grayValue).getRGB();
                grayscaleImage.setRGB(i, j, grayColor);
            }
        }

        // Save the grayscale image

        log.info("Image converted to grayscale.");
        File outputfile = new File("image.jpg");
        ImageIO.write(grayscaleImage, "jpg", outputfile);
        return grayscaleImage;
    }




}