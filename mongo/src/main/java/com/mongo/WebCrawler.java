package com.mongo;

import com.mongo.Entity.URL;
import com.mongo.Repository.URLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class WebCrawler {

    @Autowired
    URLRepository URLRepo;

    @Autowired
    MongoTemplate template;

    private ArrayList<String> seeds = new ArrayList<>();

    public void run(){
        for(String url : seeds){
            Scraper crawl = new Scraper(url);
            if(!checkURLScanner(url)) {
                crawl.disableLogs();
                String textContent = crawl.getTextFromSite();
                if(textContent != null && textContent.toCharArray().length > 10) {
                    URL scannedURL = new URL(url, textContent);
                    URLRepo.save(scannedURL);
                }
            } else {
                System.out.println("URL already added");
            }
            for(String urlLayer2 : crawl.getLinks().keySet()){
                Scraper crawlLayer2 = new Scraper(urlLayer2);
                if(!checkURLScanner(urlLayer2)) {
                    crawlLayer2.disableLogs();
                    String textContentLayer2 = crawlLayer2.getTextFromSite();
                    if(textContentLayer2 != null && textContentLayer2.toCharArray().length > 10) {
                        URL scannedURLLayer2 = new URL(urlLayer2, textContentLayer2);
                        URLRepo.save(scannedURLLayer2);
                    }
                } else {
                    System.out.println("URL already added");
                }
                for(String urlLayer3 : crawlLayer2.getLinks().keySet()){
                    Scraper crawlLayer3 = new Scraper(urlLayer3);
                    if(!checkURLScanner(urlLayer3)) {
                        crawlLayer3.disableLogs();
                        String textContentLayer3 = crawlLayer3.getTextFromSite();
                        if(textContentLayer3 != null && textContentLayer3.toCharArray().length > 10) {
                            URL scannedURLLayer3 = new URL(urlLayer3, textContentLayer3);
                            URLRepo.save(scannedURLLayer3);
                        }
                    } else {
                        System.out.println("URL already added");
                    }
                }
            }
        }

        System.out.println("Completed:");
    }

    public boolean checkURLScanner(String url){
        Query query = new Query();
        query.addCriteria(Criteria.where("url").is(url));
        return template.exists(query, URL.class);
    }

    public void addSeed(String url){
        seeds.add(url);
    }
}
