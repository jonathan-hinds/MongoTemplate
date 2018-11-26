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
    WebCrawler webCrawler;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        String[] seeds = {
          "https://en.wikipedia.org/wiki/Web_design",
          "https://www.thebest10websitebuilders.com/charts/1/best-website-builders?utm_source=google&utm_medium=web%20design%20tools^e^247788941225^1t3&utm_campaign=ma_thebest10websitebuilders.com_us_e^ds_lowQS_x",
          "https://www.creativebloq.com/features/best-web-design-tools",
          "https://nlp.stanford.edu/IR-book/html/htmledition/stemming-and-lemmatization-1.html",
          "https://artsandculture.google.com/project/street-art",
          "https://en.wikipedia.org/wiki/Street_art",
          "https://www.juxtapoz.com/street-art/",
          "https://www.awwwards.com/books/",
          "https://en.wikipedia.org/wiki/Applied_mathematics",
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

        for(String seed : seeds){
            webCrawler.addSeed(seed);
        }

        webCrawler.run();
    }
}


