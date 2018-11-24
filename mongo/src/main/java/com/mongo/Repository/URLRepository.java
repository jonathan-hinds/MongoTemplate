package com.mongo.Repository;

import com.mongo.Entity.URL;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface URLRepository extends MongoRepository<URL, String> {
}
