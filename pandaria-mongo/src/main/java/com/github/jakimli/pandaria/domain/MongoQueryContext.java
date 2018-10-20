package com.github.jakimli.pandaria.domain;

import com.github.jakimli.pandaria.domain.wait.Waitable;
import com.mongodb.BasicDBList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class MongoQueryContext implements Waitable<BasicDBList> {

    private String collection;

    @Autowired
    MongoClient mongo;
    private BasicDBList result;

    public void collection(String collection) {
        this.collection = collection;
    }

    public BasicDBList findAll() {
        result = mongo.findAll(collection);
        return result;
    }

    @Override
    public void retry() {
        findAll();
    }

    @Override
    public BasicDBList result() {
        return this.result;
    }
}
