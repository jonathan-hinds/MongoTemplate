package com.mongo.Services;

import com.mongo.Entity.URL;
import com.mongo.Repository.URLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class URLService {

    @Autowired
    URLRepository URLRepo;

}
