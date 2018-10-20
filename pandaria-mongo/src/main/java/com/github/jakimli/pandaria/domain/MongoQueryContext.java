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
    private String filter;

    public void collection(String collection) {
        this.collection = collection;
    }

    @Override
    public void retry() {
        find();
    }

    @Override
    public BasicDBList result() {
        return this.result;
    }

    public BasicDBList find() {

        if (filter != null) {
            result = mongo.find(collection, filter);
        } else {
            result = mongo.findAll(collection);
        }

        return result;
    }

    public void filter(String filter) {
        this.filter = filter;
    }
}
