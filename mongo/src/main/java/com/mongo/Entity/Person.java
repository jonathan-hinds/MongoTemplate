package com.mongo.Entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "person")
public class Person {

    private static final String AGE_FEILD = "age";
    private static final String NAME_FEILD = "name";

    @Field(NAME_FEILD)
    private String name = "";
    @Field(AGE_FEILD)
    private int age = 0;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
