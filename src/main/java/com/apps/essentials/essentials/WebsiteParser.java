package com.apps.essentials.essentials;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebsiteParser {
    public static void main(String[] args) {
        try {

            // Connect to the website and fetch the HTML document
            // Replace with the website you want to parse
            String url = "https://www.kambimalayalamkathakal.com/series/aliyum-anju-penmakkalum-kambi-novel/page/2/";
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")  // Simulate a browser request
                    .timeout(5000)            // Set connection timeout
                    .get();

            // Extract the page title
            String pageTitle = doc.title();
            System.out.println("Page Title: " + pageTitle);

            // Extract all links from the page
            System.out.println("\nLinks on the page:");
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                System.out.println("Text: " + link.text());
                System.out.println("URL: " + link.attr("href"));
            }

            // Extract specific elements by CSS selector
            System.out.println("\nHeadings:");
            Elements headings = doc.select("h1, h2, h3");
            for (Element heading : headings) {
                System.out.println(heading.text());
            }

        } catch (IOException e) {
            System.err.println("Error parsing website: " + e.getMessage());
            e.printStackTrace();
        }
    }
}