package com.mongo;

import com.mongo.Entity.Person;
import com.mongo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    UserRepository userRepo;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        Person person = new Person("Jon", 23);
        this.userRepo.save(person);
    }
}
