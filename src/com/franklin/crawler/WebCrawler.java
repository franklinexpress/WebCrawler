package com.franklin.crawler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {
    Queue<String> queue;
    List<String> discoveredSitesList;

    public WebCrawler() {
        this.queue = new LinkedList<>();
        this.discoveredSitesList = new ArrayList<>();
    }

    //if you look carefully, you see this is just a BFS without the Node/Vertex (using URLs)
    public void discoverWeb(String root) {
        //root = URL
        this.queue.add(root);
        this.discoveredSitesList.add(root);
        while (!queue.isEmpty()) {
            String v = queue.remove();
            String rawHtml = readURL(v);
            String regexp = "https://(\\w+\\.)*(\\w+)";
            Pattern pattern = Pattern.compile(regexp);
            Matcher matcher = pattern.matcher(rawHtml);
            while (matcher.find()) {
                String url = matcher.group();
                if (!discoveredSitesList.contains(url)) {
                    discoveredSitesList.add(url);
                    System.out.println("FOUND : " + url);
                    queue.add(url);
                }

            }
        }
    }



    public String readURL(String v) {
        String rawHTML = "";
        try {
            URL url = new URL(v);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine = "";
            while ((inputLine = in.readLine()) != null) {
                rawHTML += inputLine;
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxx     FILE NOT FOUND ERROR     xxxxxxxxxxxxxxxxxxxxxxxx");
        } catch (MalformedURLException e) {
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxx           URL ERROR           xxxxxxxxxxxxxxxxxxxxxxxx");
        } catch (IOException e) {
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxx           IO ERROR           xxxxxxxxxxxxxxxxxxxxxxxx");
        }
        return rawHTML;
    }

    public static void main(String[] args) {
        WebCrawler crawler = new WebCrawler();
        crawler.discoverWeb("http://www.google.com");
    }
}
