package com.mongo;
import com.mongo.Entity.URL;
import com.mongo.Repository.URLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    URLRepository URLRepo;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        ArrayList<String> visited = new ArrayList<>();

        String[] seeds = {
                "https://en.wikipedia.org/wiki/Quantum_computing",
                "https://en.wikipedia.org/wiki/Artificial_intelligence",
                "https://en.wikipedia.org/wiki/Artificial_neural_network",
                "https://en.wikipedia.org/wiki/Cyber-security_regulation",
                "https://en.wikipedia.org/wiki/Trachoma",
                "https://en.wikipedia.org/wiki/Evolutionary_algorithm",
                "https://en.wikipedia.org/wiki/Genetic_algorithm",
                "https://en.wikipedia.org/wiki/Text_mining",
                "https://en.wikipedia.org/wiki/Data_mining",
                "https://en.wikipedia.org/wiki/Golden_ratio",
                "https://en.wikipedia.org/wiki/Base_pair",
        };

        for(String url : seeds){
            Scraper crawl = new Scraper(url);
            if(!visited.contains(url)) {
                crawl.disableLogs();
                String textContent = crawl.getCountFromUrl();
                if(textContent.toCharArray().length > 10) {
                    URL scannedURL = new URL(url, textContent);
                    URLRepo.save(scannedURL);
                }
                visited.add(url);
            }
            for(String urlLayer2 : crawl.getLinks().keySet()){
                Scraper crawlLayer2 = new Scraper(urlLayer2);
                if(!visited.contains(urlLayer2)) {
                    crawlLayer2.disableLogs();
                    String textContentLayer2 = crawlLayer2.getCountFromUrl();
                    if(textContentLayer2.toCharArray().length > 10) {
                        URL scannedURLLayer2 = new URL(urlLayer2, textContentLayer2);
                        URLRepo.save(scannedURLLayer2);
                    }
                    visited.add(urlLayer2);
                }
                for(String urlLayer3 : crawlLayer2.getLinks().keySet()){
                    Scraper crawlLayer3 = new Scraper(urlLayer2);
                    if(!visited.contains(urlLayer3)) {
                        crawlLayer3.disableLogs();
                        String textContentLayer3 = crawlLayer3.getCountFromUrl();
                        if(textContentLayer3.toCharArray().length > 10) {
                            URL scannedURLLayer3 = new URL(urlLayer3, textContentLayer3);
                            URLRepo.save(scannedURLLayer3);
                        }
                        visited.add(urlLayer3);
                    }
                }
            }
        }
    }
}
