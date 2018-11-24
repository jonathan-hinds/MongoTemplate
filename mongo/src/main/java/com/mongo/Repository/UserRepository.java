package com.mongo.Repository;

import com.mongo.Entity.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<Person, String> {
}
