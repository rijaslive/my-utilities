//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.apps.essentials.essentials.service.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.LayoutProcessor;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PdfCreator {
    @Autowired
    @Qualifier("malayalamFont")
    private Font malayalamFont;

    public PdfCreator() {
    }

    @PostConstruct
    public void init() {
        LayoutProcessor.enableKernLiga();
    }

    public static void main(String[] args) {
        String outputFilePath = "output.pdf";
        float margin = 70.875F;
        LayoutProcessor.enableKernLiga();
        Document document = new Document(PageSize.A4, margin, margin, margin, margin);

        try {
            PdfWriter.getInstance(document, new FileOutputStream(outputFilePath));
            document.open();
            String fontPath = "NotoSansMalayalam-Regular.ttf";
            BaseFont baseFont = BaseFont.createFont(fontPath, "Identity-H", true);
            Font malayalamFont = new Font(baseFont, 12.0F, 0);
            String data = readFile("input.txt");
            Paragraph paragraph = new Paragraph(data, malayalamFont);
            document.add(paragraph);
        } catch (DocumentException | IOException e) {
            ((Exception)e).printStackTrace();
        } finally {
            document.close();
        }

    }

    public ByteArrayOutputStream generatePDF(MultipartFile inputTextFile) {
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        float margin = 70.875F;
        Document document = new Document(PageSize.A4);

        try {
            PdfWriter.getInstance(document, pdfOutputStream);
            document.open();
            String data = readFile(inputTextFile.getInputStream());
            String fontPath = "NotoSansMalayalam-Regular.ttf";
            BaseFont baseFont = BaseFont.createFont(fontPath, "Identity-H", true);
            new Font(baseFont, 12.0F, 0);
            Paragraph paragraph = new Paragraph(data, this.malayalamFont);
            document.add(paragraph);
        } catch (DocumentException | IOException e) {
            ((Exception)e).printStackTrace();
        } finally {
            document.close();
        }

        return pdfOutputStream;
    }

    public static String readFile(InputStream is) {
        StringBuilder contentBuilder = new StringBuilder();

        String line;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            while((line = reader.readLine()) != null) {
                contentBuilder.append(line);
                contentBuilder.append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }

    public static String readFile(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();

        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while((line = reader.readLine()) != null) {
                contentBuilder.append(line);
                contentBuilder.append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }
}
