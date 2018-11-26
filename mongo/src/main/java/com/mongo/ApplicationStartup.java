package com.mongo;
import com.mongo.Config.MongoConfig;
import com.mongo.Entity.URL;
import com.mongo.Repository.URLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    URLRepository URLRepo;

    @Autowired
    MongoTemplate template;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        /*
            https://en.wikipedia.org/wiki/Web_design
            https://www.thebest10websitebuilders.com/charts/1/best-website-builders?utm_source=google&utm_medium=web%20design%20tools^e^247788941225^1t3&utm_campaign=ma_thebest10websitebuilders.com_us_e^ds_lowQS_x
            https://www.creativebloq.com/features/best-web-design-tools
            https://nlp.stanford.edu/IR-book/html/htmledition/stemming-and-lemmatization-1.html
            https://artsandculture.google.com/project/street-art
            https://en.wikipedia.org/wiki/Street_art
            https://www.juxtapoz.com/street-art/
            https://www.awwwards.com/books/
            https://en.wikipedia.org/wiki/Applied_mathematics
         */

        String[] seeds = {
                //"https://en.wikipedia.org/wiki/Computer_animation",
                //"https://en.wikipedia.org/wiki/Animation",
                //"https://en.wikipedia.org/wiki/Traditional_animation",
                //"https://en.wikipedia.org/wiki/3D_modeling",
                //"https://en.wikipedia.org/wiki/Anime",
                "https://en.wikipedia.org/wiki/Comics",
                "https://en.wikipedia.org/wiki/Sony",
                "https://en.wikipedia.org/wiki/Data_structure",
                "https://en.wikipedia.org/wiki/Data_science",
                "https://en.wikipedia.org/wiki/Arcade_game",
                "https://en.wikipedia.org/wiki/Flash_animation",
                "https://en.wikipedia.org/wiki/Study_skills",
                "https://en.wikipedia.org/wiki/Research",
                "https://en.wikipedia.org/wiki/Cartoon",
                "https://en.wikipedia.org/wiki/History_of_animation",
                "https://en.wikipedia.org/wiki/Google",
                "https://stackoverflow.com/questions/6800509/are-there-apis-for-text-analysis-mining-in-java",
        };

        for(String url : seeds){
            Scraper crawl = new Scraper(url);
            if(!checkURLScanner(url)) {
                crawl.disableLogs();
                String textContent = crawl.getCountFromUrl();
                if(textContent != null && textContent.toCharArray().length > 10) {
                    URL scannedURL = new URL(url, textContent);
                    URLRepo.save(scannedURL);
                }
            }
            for(String urlLayer2 : crawl.getLinks().keySet()){
                Scraper crawlLayer2 = new Scraper(urlLayer2);
                if(!checkURLScanner(urlLayer2)) {
                    crawlLayer2.disableLogs();
                    String textContentLayer2 = crawlLayer2.getCountFromUrl();
                    if(textContentLayer2 != null && textContentLayer2.toCharArray().length > 10) {
                        URL scannedURLLayer2 = new URL(urlLayer2, textContentLayer2);
                        URLRepo.save(scannedURLLayer2);
                    }
                }
                for(String urlLayer3 : crawlLayer2.getLinks().keySet()){
                    Scraper crawlLayer3 = new Scraper(urlLayer3);
                    if(!checkURLScanner(urlLayer3)) {
                        crawlLayer3.disableLogs();
                        String textContentLayer3 = crawlLayer3.getCountFromUrl();
                        if(textContentLayer3 != null && textContentLayer3.toCharArray().length > 10) {
                            URL scannedURLLayer3 = new URL(urlLayer3, textContentLayer3);
                            URLRepo.save(scannedURLLayer3);
                        }
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
}


