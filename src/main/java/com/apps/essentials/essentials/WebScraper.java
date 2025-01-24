package com.apps.essentials.essentials;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class WebScraper {

    private static final String USER_AGENT = "Mozilla/5.0";

    public static void scrapeStory(List<String> pageUrls, String filePath) {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<String>> futures = new ArrayList<>();

        for (String url : pageUrls) {
            futures.add(executor.submit(() -> extractContent(url)));
//            extractContent(url);
        }

        File file = new File(filePath);
        File directory = file.getParentFile();
        if (directory != null && !directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directory created: " + directory.getAbsolutePath());
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            for (Future<String> future : futures) {
                try {
                    String content = future.get();
                    if (content != null) {
                        writer.write("\n" + content);
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    public static String extractContent(String link) {
        try {
            Document doc = Jsoup.connect(link)
                    .userAgent(USER_AGENT)
                    .get();
            Element contentDiv = doc.selectFirst("div.entry-content");
            if (contentDiv != null) {
                String content = contentDiv.text();
                int startIdx = content.indexOf("തുടർന്ന് വായിക്കുക");
                int endIdx1 = content.indexOf("(തുടരും)");


                if (startIdx != -1 && endIdx1 != -1) {
                    content = content.substring(startIdx + "തുടർന്ന് വായിക്കുക".length(), endIdx1);
                }
                int endIdx2 = content.indexOf("നിങ്ങളുടെ വിലയേറിയ അഭിപ്രായങ്ങൾ");
                if (endIdx2 != -1) {
                    content = content.substring(0, endIdx2);
                }
                int endIdx3 = content.indexOf("സമാന കമ്പികഥകൾ");
                if (endIdx3 != -1) {
                    content = content.substring(0, endIdx3);
                }
                System.out.println(content+"\n");
                return content;
            } else {
                System.out.println("No div with class 'entry-content' found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String baseUrl = "https://www.kambimalayalamkathakal.com/series/mallu-couple-swapping/page/";
        String filePath = "stories/mallu-couple-swapping.txt";
        int lastPage = 2;

        for (int pageNum = lastPage; pageNum > 0; pageNum--) {
            String url = baseUrl + pageNum + "/";
            try {
                Document doc = Jsoup.connect(url)
                        .userAgent(USER_AGENT)
                        .get();
                Elements h2Elements = doc.select("h2.entry-title");
                List<String> links = new ArrayList<>();
                for (Element h2 : h2Elements) {
                    String link = h2.select("a").attr("href");
                    links.add(link);
                }
                scrapeStory(links, filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
